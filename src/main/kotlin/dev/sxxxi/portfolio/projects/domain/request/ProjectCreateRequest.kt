package dev.sxxxi.portfolio.projects.domain.request

data class ProjectCreateRequest(
    val title: String,
    val description: String,
    val repoLink: String?,
    val deployedLink: String?
)