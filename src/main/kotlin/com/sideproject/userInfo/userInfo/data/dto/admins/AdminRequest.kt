package com.sideproject.userInfo.userInfo.data.dto.admins

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AdminRequest(
    @field :NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field :Size(min = 5, max = 12, message = "아이디는 5~12자 이여야 합니다.")
    var username: String,

    @field :NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @field :Pattern(
        regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{5,10}$",
        message = "닉네임은 특수문자를 제외한 5~12자리여야 합니다."
    )
    var nickName: String,

    var role: String,

    @field :NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 이여야 합니다.")
    @field :Pattern(
        regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
        message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
    )
    var password: String,
)

data class LoginRequest(
    @field :NotBlank(message = "아이디는 필수 입력 값입니다.")
    @field :Size(min = 5, max = 12, message = "아이디는 5~12자 이여야 합니다.")
    var username: String,

    @field :NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 이여야 합니다.")
    var password: String
)