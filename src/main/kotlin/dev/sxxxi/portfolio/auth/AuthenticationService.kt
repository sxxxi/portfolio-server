package dev.sxxxi.portfolio.auth

import java.util.*

interface AuthenticationService {
    /**
     * This is supposed to return a JWT containing the list of Authority
     */
    fun register(username: String, password: String): String

    fun login(username: String, password: String): Optional<String>
}