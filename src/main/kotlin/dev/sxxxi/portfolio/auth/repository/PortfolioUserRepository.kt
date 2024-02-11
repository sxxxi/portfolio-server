package dev.sxxxi.portfolio.auth.repository

import dev.sxxxi.portfolio.auth.model.PortfolioUser
import dev.sxxxi.portfolio.core.repository.BaseRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PortfolioUserRepository : BaseRepository<PortfolioUser, UUID> {
    fun findUserByUsername(username: String): Optional<PortfolioUser>
    fun existsByUsername(username: String): Boolean
}