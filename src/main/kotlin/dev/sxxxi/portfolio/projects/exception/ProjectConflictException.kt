package dev.sxxxi.portfolio.projects.exception

import dev.sxxxi.portfolio.core.exception.ConflictException

class ProjectConflictException(reason: String) : ConflictException(reason)