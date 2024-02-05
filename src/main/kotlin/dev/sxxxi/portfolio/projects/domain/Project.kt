package dev.sxxxi.portfolio.projects.domain

data class Project(
    val title: String,
    val description: String,
    val imageLinks: List<String>,
    val repoLink: String?,
    val deployedLink: String?
)