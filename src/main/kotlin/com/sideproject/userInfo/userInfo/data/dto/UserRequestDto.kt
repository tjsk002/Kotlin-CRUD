package com.sideproject.userInfo.userInfo.data.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size



class UserRequestDto(
    val userData: UserRequest

)

class UserRequest(
    @field:NotBlank(message = "username is required value.")
    @field:Size(min = 5, max = 12, message = "username is between 5 and 12.")
    val username: String,

    @field:NotBlank(message = "nickName is required value.")
    val nickName: String,

    @field:Pattern(regexp = "^(female|male)$", message = "gender must be either 'female' or 'male'.")
    val gender: String,

    val isActive: Boolean,

    @field:NotBlank(message = "type is required value")
    @field:Pattern(regexp = "^(front|back|dba|infra)$", message = "type must be one of the following values: 'front', 'back', 'dba', or 'infra'."
    )
    val type: String,

    val description: String?
)

