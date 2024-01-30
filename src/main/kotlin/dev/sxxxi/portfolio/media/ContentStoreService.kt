package dev.sxxxi.portfolio.media

import dev.sxxxi.portfolio.media.domain.Services
import org.springframework.web.multipart.MultipartFile

interface ContentStoreService {
    fun storeContent(serviceName: Services, file: MultipartFile): String
    fun getContent(key: String): String
}