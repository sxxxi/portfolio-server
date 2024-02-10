package dev.sxxxi.portfolio.auth.jwt

import io.fusionauth.jwt.domain.JWT
import org.springframework.security.core.userdetails.UserDetails

interface JwtService {
    fun issue(userDetails: UserDetails): String
    fun verify(jws: String): JWT
}