package dev.sxxxi.portfolio.projects.domain.response

import dev.sxxxi.portfolio.projects.domain.Project

data class ProjectListResponse(
    val projects: List<Project>
)