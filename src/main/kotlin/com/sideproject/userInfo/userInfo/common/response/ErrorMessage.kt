package com.sideproject.userInfo.userInfo.common.response

class ErrorMessage {
    companion object {
        const val USER_NOT_FOUND = "Username not found"
        const val DUPLICATE_USER = "duplicate username"
        const val USER_ALREADY_EXISTS = "Username already exists"

        // 회원가입 & 계정 관련 에러
        const val USERNAME_ALREADY_EXISTS = "Username already exists"
        const val INVALID_USERNAME_FORMAT = "Invalid username format"
        const val MISSING_REQUIRED_FIELDS = "Missing required fields"
        const val FAILED_TO_CREATE_USER = "Failed to create user"
        const val SERVER_ERROR_CREATE_USER = "Failed to create user due to server error"

        // 로그인 관련 에러
        const val INVALID_USERNAME_OR_PASSWORD = "Invalid username or password"
        const val USERNAME_NOT_FOUND = "Username not found"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val TOKEN_EXPIRED = "Token has expired"
        const val LOGIN_SERVER_ERROR = "Login failed due to server error"
        const val AUTHENTICATION_ERROR = "Authentication failed"
        const val NO_TOKEN_PROVIDED = "No token provided"

        // 로그아웃 관련 에러
        const val NO_AUTHENTICATION_INFORMATION = "No authentication information"
        const val ALREADY_LOGGED_OUT = "Already logged out"
        const val INVALID_TOKEN = "Invalid token"
    }
}