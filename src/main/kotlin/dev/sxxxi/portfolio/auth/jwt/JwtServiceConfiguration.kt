package dev.sxxxi.portfolio.auth.jwt

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtServiceConfiguration {
    @Bean
    fun params(): JwtServiceParameters {
        return JwtServiceParameters(
            jwtValidDays = 1L
        )
    }
}