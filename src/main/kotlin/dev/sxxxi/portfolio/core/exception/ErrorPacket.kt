package dev.sxxxi.portfolio.core.exception

import java.time.LocalDateTime

data class ErrorPacket(
    val code: Int,
    val message: String,
    val timestamp: String = LocalDateTime.now().toString()
)