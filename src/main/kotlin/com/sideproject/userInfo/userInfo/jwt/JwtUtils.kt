package com.sideproject.userInfo.userInfo.jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    @Value("\${spring.jwt.secret-key}") private val secret: String
) {
    private val key: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
    private val expiredMs = 60 * 60 * 10L

    fun getUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("username", String::class.java)
    }

    fun getRole(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String::class.java)
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .expiration.before(Date())
    }

    fun createJwtToken(username: String, role: String): String {
        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiredMs))
            .signWith(key)
            .compact()
    }

    // 토큰에서 username 파싱
    fun parseUsername(token: String): String {
        return ""
    }

    // UsernamePasswordAuthenticationToken 데이터 조회
    fun getAuthentication(username: String): Authentication? {
        return null
    }

    // Claims 조회
    private fun getAllClaims(token: String) {

    }
}