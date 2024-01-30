package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatus

open class InternalErrorException(message: String) : WebException(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = message)