package dev.sxxxi.portfolio.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AuthenticationServiceImpl(
    private val portfolioUserRepository: PortfolioUserRepository,
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

    override fun register(username: String, password: String): String {
        portfolioUserRepository.save(PortfolioUser(
            username = username,
            password = password
        ))
        // TODO: RETURN JWT HERE
        return "JWT PLACEHOLDER"
    }

    override fun login(username: String, password: String): Optional<String> {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication = authenticationManager.authenticate(authToken)

        if (!authentication.isAuthenticated)
            return Optional.empty()

        return portfolioUserRepository.findUserByUsername(username).let { oUser ->
            if (oUser.isEmpty) return Optional.empty()

            // Put authorities in JWT here

            return Optional.of("JWT PLACEHOLDER")
        }
    }


}