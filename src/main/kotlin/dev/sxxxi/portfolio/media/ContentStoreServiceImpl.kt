package dev.sxxxi.portfolio.media

import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import dev.sxxxi.portfolio.core.config.utils.elapsed
import dev.sxxxi.portfolio.core.config.utils.minutesFromNow
import dev.sxxxi.portfolio.media.domain.Services
import dev.sxxxi.portfolio.media.exception.ContentTypeNotSupported
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ContentStoreServiceImpl : ContentStoreService {

    private val s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build()
    private val logger = LoggerFactory.getLogger(ContentStoreServiceImpl::class.java)

    override fun storeContent(serviceName: Services, file: MultipartFile): String {
        val fileSuffix = file.originalFilename?.split(".")?.get(1)
            ?: throw ContentTypeNotSupported("File suffix missing.")
        val contentType = when (file.contentType) {
            "image/jpeg", "image/png" -> "img"
            "video/mp4", "video/mpeg" -> "video"
            else -> throw ContentTypeNotSupported("Content type not supported.")
        }
        val fileName = genFileName(file)
        val tempFile = File.createTempFile(fileName, ".$fileSuffix")
        val path = Path.of(ROOT_DIR, serviceName.path, contentType, tempFile.name).toString()

        file.transferTo(tempFile)
        s3.putObject(PutObjectRequest(BUCKET_NAME, path, tempFile))

        return path
    }

    private fun genFileName(file: MultipartFile): String {
        val md = MessageDigest.getInstance("MD5")
        val bos = ByteArrayOutputStream()
        val epochBytes = ByteBuffer.allocate(Long.SIZE_BYTES)

        elapsed(logger) {
            bos.writeBytes(file.inputStream.use { it.readAllBytes() })
        }
        epochBytes.put(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toByte())
        bos.write(epochBytes.array())

        return elapsed(logger) {
            md.digest(bos.use { it.toByteArray() })
                .joinToString("") { "%02x".format(it) }
        }
    }

    override fun getContent(key: String): String {
        return s3.generatePresignedUrl(BUCKET_NAME, key, minutesFromNow(PRE_SIGNED_URL_VALIDITY_MINUTES)).toString()
    }

    // TODO: Create a mechanism to switch S3 client region with environment variables for
    //  easy deployments to other regions. The bucket name too.
    companion object {
        private const val ROOT_DIR = "portfolio"
        private const val BUCKET_NAME = "seijiakakabe-991617069"
        private const val PRE_SIGNED_URL_VALIDITY_MINUTES = 1
    }
}