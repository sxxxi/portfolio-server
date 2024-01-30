package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatus

open class BadRequestException(message: String) : WebException(code = HttpStatus.BAD_REQUEST, reason = message)