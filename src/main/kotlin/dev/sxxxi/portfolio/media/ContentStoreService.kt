package dev.sxxxi.portfolio.media

import dev.sxxxi.portfolio.media.domain.Services
import org.springframework.web.multipart.MultipartFile

interface ContentStoreService {
    fun store(serviceName: Services, file: MultipartFile): String
    fun get(key: String): String
    fun delete(key: String)
}