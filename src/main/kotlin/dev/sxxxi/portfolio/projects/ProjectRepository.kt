package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.core.repository.BaseRepository
import dev.sxxxi.portfolio.projects.domain.Project
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: BaseRepository<Project, Long>