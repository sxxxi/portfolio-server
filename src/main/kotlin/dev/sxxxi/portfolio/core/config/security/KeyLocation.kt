package dev.sxxxi.portfolio.core.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("keys.location")
class KeyLocation(
    var publicKey: String,
    var privateKey: String
)