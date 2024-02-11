package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.repository.PortfolioUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustUserDetailsService(
    private val portfolioUserRepository: PortfolioUserRepository
) : UserDetailsService{

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val oUser = portfolioUserRepository.findUserByUsername(username)
            if (oUser.isPresent) {
                return oUser.get()
            }
        }
        throw UsernameNotFoundException("User with the given username not found")
    }
}