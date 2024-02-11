package dev.sxxxi.portfolio.projects

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository: JpaRepository<ProjectEntity, Long>