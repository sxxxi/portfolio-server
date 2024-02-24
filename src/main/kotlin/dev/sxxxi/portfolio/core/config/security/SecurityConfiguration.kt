package dev.sxxxi.portfolio.core.config.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import dev.sxxxi.portfolio.auth.model.Role
import dev.sxxxi.portfolio.auth.model.RoleEnum
import org.slf4j.LoggerFactory
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
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec


@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)
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
    fun keyPair(keyLocation: KeyLocation): KeyPair {
        logger.info(keyLocation.publicKey, keyLocation.privateKey)
        val x = PKCS8EncodedKeySpec(readFileAsBytes(keyLocation.privateKey))
        val y = X509EncodedKeySpec(readFileAsBytes(keyLocation.publicKey))
        val kf = KeyFactory.getInstance("RSA")
        val privateKey = kf.generatePrivate(x)
        val publicKey = kf.generatePublic(y)

        return KeyPair(publicKey, privateKey)

//        return KeyPairGenerator.getInstance("RSA")
//            .apply{ initialize(4096) }
//            .genKeyPair()
    }

    private fun readFileAsBytes(path: String): ByteArray {
        return Files.readAllBytes(Paths.get(path))
    }

    @Bean
    fun jwtDecoder(keyPair: KeyPair): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(keyPair.public as RSAPublicKey?).build()
    }

    @Bean
    fun jwtEncoder(keyPair: KeyPair): JwtEncoder {
        val jwk = RSAKey.Builder(keyPair.public as RSAPublicKey)
            .privateKey(keyPair.private).build()
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

    @Bean
    fun webMvcConfigure(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                super.addCorsMappings(registry)
                registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "OPTIONS")
            }
        }

    }
}