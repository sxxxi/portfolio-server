package dev.sxxxi.portfolio.auth.jwt

import dev.sxxxi.portfolio.auth.Authority
import dev.sxxxi.portfolio.core.exception.ForbiddenException
import io.fusionauth.jwt.JWTExpiredException
import io.fusionauth.jwt.domain.JWT
import io.fusionauth.jwt.rsa.RSASigner
import io.fusionauth.jwt.rsa.RSAVerifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.time.ZonedDateTime

@Service
class JwtServiceImpl(
    private val params: JwtServiceParameters,
) : JwtService {

    override fun issue(userDetails: UserDetails): String {
        val signer = RSASigner.newSHA256Signer(keypair.private)

        val jwt = JWT().setIssuer("Seiji Akakabe")      // TODO: Create a configuration property class for this
            .setSubject(userDetails.username)
            .setIssuedAt(ZonedDateTime.now())
            .setExpiration(getExpiration(deltaDays = params.jwtValidDays))
            .addClaim(IS_ADMIN_KEY, userDetails.authorities.contains(Authority.ADMIN))

        return JWT.getEncoder().encode(jwt, signer)
    }

    override fun verify(jws: String): JWT {
        val verifier = RSAVerifier.newVerifier(keypair.public)
        try {
            return JWT.getDecoder().decode(jws, verifier)
        } catch (e: JWTExpiredException) {
            throw ForbiddenException("The provided Token is expired")
        }
    }

    companion object {
        private const val IS_ADMIN_KEY = "admin"
        val keypair: KeyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()

        private fun getExpiration(deltaDays: Long = 0, deltaHours: Long = 0): ZonedDateTime {
            return ZonedDateTime.now().plusDays(deltaDays).plusHours(deltaHours)
        }
    }
}
