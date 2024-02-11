package dev.sxxxi.portfolio.auth.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class JwtServiceImpl(
    private val jwtEncoder: JwtEncoder
) : JwtService {
    override fun issue(authentication: Authentication): String {
        // Get roles and chuck inside jwt
        val roles = authentication.authorities
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "))

        val claims = JwtClaimsSet.builder()
            .issuer("Seiji Akakabe")
            .subject(authentication.name)
            .expiresAt(JwtUtils.getExpiration(1).toInstant())
            .claim("roles", roles)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}