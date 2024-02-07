package dev.sxxxi.portfolio

import dev.sxxxi.portfolio.core.bootstrap.AdminProfile
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AdminProfile::class)
class PortfolioApplication

fun main(args: Array<String>) {
	runApplication<PortfolioApplication>(*args)
}
