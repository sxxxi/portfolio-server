package dev.sxxxi.portfolio.core.bootstrap

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("admin")
class AdminProfile (
    var username: String,
    var password: String
)