package dev.sxxxi.portfolio

import dev.sxxxi.portfolio.core.bootstrap.AdminProfile
import dev.sxxxi.portfolio.core.config.security.KeyLocation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AdminProfile::class, KeyLocation::class)
class PortfolioApplication

fun main(args: Array<String>) {
	runApplication<PortfolioApplication>(*args)
}
