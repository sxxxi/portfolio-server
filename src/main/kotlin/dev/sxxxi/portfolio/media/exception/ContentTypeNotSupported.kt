package dev.sxxxi.portfolio.media.exception

import dev.sxxxi.portfolio.core.exception.ForbiddenException

class ContentTypeNotSupported(message: String): ForbiddenException(message)