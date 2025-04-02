package com.sideproject.userInfo.userInfo.controller

import com.sideproject.userInfo.userInfo.common.response.RestResponse
import com.sideproject.userInfo.userInfo.data.dto.UserRequestDto
import com.sideproject.userInfo.userInfo.data.dto.UserResponseDto
import com.sideproject.userInfo.userInfo.data.dto.UsersDto
import com.sideproject.userInfo.userInfo.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping()
    fun getUsersList(
        @PageableDefault(size = 10, direction = Sort.Direction.DESC) pageable: Pageable,
    ): RestResponse<UserResponseDto> {
        return RestResponse.success(userService.getUserList(pageable))
    }

    @GetMapping("/{userId}")
    fun getUsersDetail(
        @PathVariable(name = "userId", required = true) userId: Long
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.getUserDetail(userId))
    }

    @PostMapping
    fun createUser(
        @RequestBody @Valid userRequestDto: UserRequestDto
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.createUser(userRequestDto))
    }

    @PutMapping("/{userId}")
    fun editUser(
        @PathVariable(name = "userId", required = true) userId: Long,
        @RequestBody userRequestDto: UserRequestDto
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.editUser(userId, userRequestDto))
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable(name = "userId", required = true) userId: Long
    ): RestResponse<UsersDto> {
        return RestResponse.success(userService.deleteUser(userId))
    }
}