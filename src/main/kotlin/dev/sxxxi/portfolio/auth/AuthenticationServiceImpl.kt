package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.jwt.JwtService
import dev.sxxxi.portfolio.core.exception.ForbiddenException
import dev.sxxxi.portfolio.core.exception.InternalErrorException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AuthenticationServiceImpl(
    private val portfolioUserRepository: PortfolioUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : UserDetailsService, AuthenticationService {

    private lateinit var authenticationManager: AuthenticationManager

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val oUser = portfolioUserRepository.findUserByUsername(username)
            if (oUser.isPresent) {
                return oUser.get()
            }
        }
        throw UsernameNotFoundException("User with the given username not found")
    }

    override fun registerUser(username: String, password: String): String {
        return saveAndReturnToken(PortfolioUser(
            username = username,
            password = passwordEncoder.encode(password),
            authorities = mutableListOf(Authority.USER)
        ))

    }

    override fun registerAdmin(username: String, password: String): String {
        return saveAndReturnToken(PortfolioUser(
            username = username,
            password = passwordEncoder.encode(password),
            authorities = mutableListOf(Authority.USER, Authority.ADMIN)
        ))
    }

    private fun saveAndReturnToken(user: PortfolioUser): String {
        return portfolioUserRepository.save(user).let { ud ->
            jwtService.issue(ud)
        }
    }

    override fun login(username: String, password: String): String {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication = authenticationManager.authenticate(authToken)

        if (!authentication.isAuthenticated)
            throw ForbiddenException("You are not authenticated.")

        return portfolioUserRepository.findUserByUsername(username).let { oUser ->
            if (oUser.isEmpty) {
                throw InternalErrorException("The user repository was not able to find the user.")
            }
            jwtService.issue(oUser.get())
        }
    }


}