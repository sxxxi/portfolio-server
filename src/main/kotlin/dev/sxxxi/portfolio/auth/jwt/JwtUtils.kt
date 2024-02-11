package dev.sxxxi.portfolio.auth.jwt

import java.util.*

class JwtUtils {

    companion object {
        fun getExpiration(day: Int = 0): Date {
            val c = Calendar.getInstance()
            c.add(Calendar.DATE, day)
            return c.time
        }
    }
}