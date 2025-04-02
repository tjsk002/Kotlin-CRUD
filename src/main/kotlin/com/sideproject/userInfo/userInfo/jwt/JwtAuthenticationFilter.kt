package com.sideproject.userInfo.userInfo.jwt

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtAuthenticationFilter : OncePerRequestFilter(

) {
    // TODO .env get()
    private val secret = "steadfastnessvlakdfnviobaenrlkjacrofuturesecretKeywlqdprkrhtlvek88888"
    private val key: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        if (token != null && isValidateToken(token)) {
            val authentication = getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        return if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else null
    }

    private fun isValidateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
            val expiration = claims.expiration
            expiration.after(Date())
            return true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        val username = claims.subject
        val role = claims["role", String::class.java]
        val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))

        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }
}