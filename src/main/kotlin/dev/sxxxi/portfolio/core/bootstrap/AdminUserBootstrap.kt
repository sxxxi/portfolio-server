package dev.sxxxi.portfolio.core.bootstrap

import dev.sxxxi.portfolio.auth.AuthenticationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Insert an administrator account on startup.
 */
@Component
class AdminUserBootstrap(
    private var admin: AdminProfile,
    private val authenticationService: AuthenticationService
) : CommandLineRunner {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun run(vararg args: String?) {
        logger.info("Register admin: ${admin.username}")
        authenticationService.registerAdmin(admin.username, admin.password)
    }
}