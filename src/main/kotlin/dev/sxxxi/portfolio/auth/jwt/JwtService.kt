package dev.sxxxi.portfolio.auth.jwt

import org.springframework.security.core.Authentication

interface JwtService {
    fun issue(authentication: Authentication): String
}