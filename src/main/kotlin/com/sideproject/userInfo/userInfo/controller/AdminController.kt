package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.admins.AdminRequest
import com.sideproject.userInfo.userInfo.data.dto.admins.LoginRequest
import com.sideproject.userInfo.userInfo.service.AdminService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Validated
class AdminController(
    private val adminService: AdminService,
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Valid adminDto: AdminRequest,
    ): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(adminService.signUpProcess(adminDto))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
    ): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(adminService.loginProcess(loginRequest))
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<RestResponse<Map<String, String>>> {
        return ResponseEntity.ok(adminService.logoutProcess(authHeader))
    }
}
