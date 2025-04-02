package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.exception.CustomBadRequestException
import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.common.response.SuccessMessage
import com.sideproject.userInfo.userInfo.data.dto.admins.AdminRequest
import com.sideproject.userInfo.userInfo.data.dto.admins.CustomAdminDetails
import com.sideproject.userInfo.userInfo.data.dto.admins.LoginRequest
import com.sideproject.userInfo.userInfo.data.entity.AdminsEntity
import com.sideproject.userInfo.userInfo.jwt.JwtUtils
import com.sideproject.userInfo.userInfo.repository.AdminsRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validation
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

@Service
class AdminService(
    private val adminsRepository: AdminsRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val response: HttpServletResponse,
    @Value("\${spring.jwt.secret-key}") private val secret: String
) {
    private val blacklistedTokens = mutableSetOf<String>()
    private val key: SecretKeySpec = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")

    fun signUpProcess(adminDto: AdminRequest): RestResponse<Map<String, String>> {
        if (isExists(adminDto.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(ErrorMessage.USERNAME_ALREADY_EXISTS)
                )
            )
        }

        adminsRepository.save(
            AdminsEntity(
                id = null,
                username = adminDto.username,
                nickName = adminDto.nickName,
                role = adminDto.role,
                password = BCryptPasswordEncoder().encode(adminDto.password),
            )
        )

        return RestResponse.success(
            mapOfParsing(SuccessMessage.SIGN_UP_SUCCESS)
        )
    }

    fun loginProcess(loginRequest: LoginRequest): RestResponse<Map<String, String>> {
        val authentication = attemptAuthentication(loginRequest)
        return successfulAuthentication(authentication)
    }

    fun logoutProcess(authHeader: String?): RestResponse<Map<String, String>> {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    mapOfParsing(ErrorMessage.NO_AUTHENTICATION_INFORMATION)
                )
            )
        }

        val token = authHeader.replace("Bearer ", "")

        if (blacklistedTokens.contains(token)) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    mapOfParsing(ErrorMessage.ALREADY_LOGGED_OUT)
                )
            )
        }

        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

            blacklistedTokens.add(token)
            return RestResponse.success(
                mapOfParsing(SuccessMessage.LOGOUT_SUCCESS)
            )
        } catch (e: ExpiredJwtException) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(
                        e.message ?: ErrorMessage.TOKEN_EXPIRED
                    )
                )
            )
        } catch (e: Exception) {
            throw CustomBadRequestException(
                RestResponse.unauthorized(
                    mapOfParsing(ErrorMessage.INVALID_TOKEN)
                )
            )
        }
    }

    private fun isExists(username: String): Boolean {
        return adminsRepository.existsByUsername(username)
    }

    private fun attemptAuthentication(loginRequest: LoginRequest): Authentication {
        val violations = Validation.buildDefaultValidatorFactory().validator.validate(loginRequest)

        if (violations.isNotEmpty()) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(ErrorMessage.LOGIN_SERVER_ERROR)
                )
            )
        }

        if (!isExists(loginRequest.username)) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(ErrorMessage.USERNAME_NOT_FOUND)
                )
            )
        }

        try {
            val authenticationToken = UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.password, null
            )

            return authenticationManager.authenticate(authenticationToken)
        } catch (e: BadCredentialsException) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(ErrorMessage.INCORRECT_PASSWORD)
                )
            )
        } catch (e: Exception) {
            throw CustomBadRequestException(
                RestResponse.badRequest(
                    mapOfParsing(ErrorMessage.LOGIN_SERVER_ERROR)
                )
            )
        }
    }

    private fun successfulAuthentication(authentication: Authentication): RestResponse<Map<String, String>> {
        val username = (authentication.principal as CustomAdminDetails).username
        val role = authentication.authorities.iterator().next().authority
        val token = jwtUtils.createJwtToken(username, role)

        response.addHeader("Authorization", "Bearer $token")
        val responseBody = RestResponse.success(
            mapOfParsing(SuccessMessage.LOGIN_SUCCESS)
        )

        return responseBody
    }

    private fun mapOfParsing(message: String): Map<String, String> {
        return mapOf(
            "message" to message
        );
    }
}