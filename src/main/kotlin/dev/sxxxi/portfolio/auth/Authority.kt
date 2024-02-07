package dev.sxxxi.portfolio.auth

import org.springframework.security.core.GrantedAuthority

enum class Authority : GrantedAuthority  {
    USER,ADMIN;

    override fun getAuthority(): String {
        return toString()
    }
}