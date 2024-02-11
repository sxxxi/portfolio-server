package dev.sxxxi.portfolio.core.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import dev.sxxxi.portfolio.auth.model.Role
import dev.sxxxi.portfolio.auth.model.RoleEnum
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**").permitAll()
                it.requestMatchers(HttpMethod.POST, "/portfolio/projects**").hasRole(Role(RoleEnum.USER).authority)
                it.requestMatchers(HttpMethod.DELETE, "/portfolio/projects**").hasRole(Role(RoleEnum.USER).authority)
                it.anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {
                    it.jwtAuthenticationConverter(jwtConverter())
                }
            }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic {}
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProviderManager(
        passwordEncoder: PasswordEncoder,
        userDetailsService: UserDetailsService
    ): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setPasswordEncoder(passwordEncoder)
        authProvider.setUserDetailsService(userDetailsService)
        return ProviderManager(authProvider)
    }

    @Bean
    fun keyPair(): KeyPair {
        return KeyPairGenerator.getInstance("RSA")
            .apply{ initialize(4096) }
            .genKeyPair()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyPair().public as RSAPublicKey?).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk = RSAKey.Builder(keyPair().public as RSAPublicKey).privateKey(keyPair().private).build()
        val keys = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(keys)
    }

    @Bean
    fun jwtConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val authConverter = JwtGrantedAuthoritiesConverter()
        authConverter.setAuthoritiesClaimName("roles")
        authConverter.setAuthoritiesClaimDelimiter(" ")
        authConverter.setAuthorityPrefix("ROLE_")
        converter.setJwtGrantedAuthoritiesConverter(authConverter)
        return converter
    }
}