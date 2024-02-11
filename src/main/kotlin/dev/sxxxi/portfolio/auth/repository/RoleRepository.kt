package dev.sxxxi.portfolio.auth.repository

import dev.sxxxi.portfolio.auth.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
}