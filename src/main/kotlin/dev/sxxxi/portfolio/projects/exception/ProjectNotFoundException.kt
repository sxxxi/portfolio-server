package dev.sxxxi.portfolio.projects.exception

import dev.sxxxi.portfolio.core.exception.NotFoundException

class ProjectNotFoundException(reason: String) : NotFoundException(reason)