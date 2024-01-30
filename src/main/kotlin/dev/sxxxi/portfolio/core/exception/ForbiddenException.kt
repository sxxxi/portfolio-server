package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatus

open class ForbiddenException(message: String) : WebException(code = HttpStatus.FORBIDDEN, reason = message)