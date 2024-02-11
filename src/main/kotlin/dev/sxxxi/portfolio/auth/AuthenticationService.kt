package dev.sxxxi.portfolio.auth

import dev.sxxxi.portfolio.auth.model.Role

interface AuthenticationService {
    fun register(username: String, password: String, roles: MutableSet<Role>)
    fun login(username: String, password: String): String
}