package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.model.AuthRequestDTO
import dev.sxxxi.portfolio.auth.model.LoginResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequestDTO
    ): LoginResponseDTO {
        return LoginResponseDTO(
            token = authenticationService.login(authRequest.username, authRequest.password)
        )

    }
}