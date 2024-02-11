package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.jwt.JwtService
import dev.sxxxi.portfolio.auth.model.PortfolioUser
import dev.sxxxi.portfolio.auth.model.Role
import dev.sxxxi.portfolio.auth.repository.PortfolioUserRepository
import dev.sxxxi.portfolio.auth.repository.RoleRepository
import dev.sxxxi.portfolio.core.exception.ConflictException
import dev.sxxxi.portfolio.core.exception.ForbiddenException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val portfolioUserRepository: PortfolioUserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {

    override fun register(username: String, password: String, roles: MutableSet<Role>) {
        if (portfolioUserRepository.existsByUsername(username)) {
            throw ConflictException("User already exists")
        }

        // Ensure roles are saved
        roleRepository.saveAll(roles)

        portfolioUserRepository.save(
            PortfolioUser(
            username = username,
            password = passwordEncoder.encode(password),
            roles = roles
        )
        )
    }

    override fun login(username: String, password: String): String {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication = authenticationManager.authenticate(authToken)

        if (!authentication.isAuthenticated)
            throw ForbiddenException("You are not authenticated.")

        return jwtService.issue(authentication)
    }


}