package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.core.repository.BaseRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface PortfolioUserRepository : BaseRepository<PortfolioUser, UUID> {
    fun findUserByUsername(username: String): Optional<PortfolioUser>
}