package dev.sxxxi.portfolio.projects

import dev.sxxxi.portfolio.core.repository.BaseRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<ProjectEntity, Long>