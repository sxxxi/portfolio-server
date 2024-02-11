package dev.sxxxi.portfolio.auth.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
class Role(
    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    val roleName: RoleEnum
) : GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    val id: Long? = null

    override fun getAuthority(): String {
        return roleName.toString()
    }
}