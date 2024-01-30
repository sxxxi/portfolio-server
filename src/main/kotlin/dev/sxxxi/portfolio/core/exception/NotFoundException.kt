package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatus

open class NotFoundException(message: String) : WebException(code = HttpStatus.NOT_FOUND, reason = message)