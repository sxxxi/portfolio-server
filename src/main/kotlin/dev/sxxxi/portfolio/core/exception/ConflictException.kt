package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatus

open class ConflictException(message: String) : WebException(code = HttpStatus.CONFLICT, reason = message)