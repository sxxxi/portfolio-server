package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.model.AuthRequestDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @GetMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequestDTO
    ): String {
        return authenticationService.login(authRequest.username, authRequest.password)
    }
}