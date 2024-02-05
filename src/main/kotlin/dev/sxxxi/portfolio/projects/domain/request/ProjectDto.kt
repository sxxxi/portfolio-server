package dev.sxxxi.portfolio.projects.domain.request

import org.springframework.web.multipart.MultipartFile

data class ProjectDto(
    val title: String,
    val description: String,
    val repoLink: String?,
    val deployedLink: String?,
    val images: MutableList<MultipartFile> = mutableListOf()
)