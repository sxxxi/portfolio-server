package dev.sxxxi.portfolio.auth.model

enum class RoleEnum(private val roleName: String) {
    ADMIN("ADMIN"), USER("USER");

    override fun toString(): String {
        return roleName
    }
}