package com.sideproject.userInfo.userInfo.service

import com.sideproject.userInfo.userInfo.common.response.ErrorMessage
import com.sideproject.userInfo.userInfo.common.response.exception.BasicException
import com.sideproject.userInfo.userInfo.data.dto.PageInfoDto
import com.sideproject.userInfo.userInfo.data.dto.UserRequestDto
import com.sideproject.userInfo.userInfo.data.dto.UserResponseDto
import com.sideproject.userInfo.userInfo.data.dto.UsersDto
import com.sideproject.userInfo.userInfo.data.entity.UsersEntity
import com.sideproject.userInfo.userInfo.repository.UsersRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService(
    private val usersRepository: UsersRepository
) {
    @Transactional(readOnly = true)
    fun getUserList(pageable: Pageable): UserResponseDto {
        val userListDto: Page<UsersEntity> = usersRepository.findAll(pageable)
        val userContent: List<UsersDto> = userListDto.map { userEntity -> UsersDto.fromEntity(userEntity) }.toList()
        val pageInfo = PageInfoDto(
            userListDto.number,
            userListDto.size,
            userListDto.totalElements,
            userListDto.totalPages
        )
        return UserResponseDto(userContent, pageInfo)
    }

    @Transactional(readOnly = true)
    fun getUserDetail(userId: Long): UsersDto {
        val findUser = findById(userId)
        return UsersDto.fromEntity(findUser)
    }

    @Transactional
    fun createUser(userRequestDto: UserRequestDto): UsersDto {
        checkDuplicateUsername(userRequestDto.userData.username)
        val newUser: UsersEntity = UsersEntity.fromDto(userRequestDto)
        val savedUser: UsersEntity = usersRepository.save(newUser)
        return UsersDto.fromEntity(savedUser)
    }

    @Transactional
    fun editUser(userId: Long, userRequestDto: UserRequestDto): UsersDto {
        val findUser: UsersEntity = findById(userId)
        val editedUser: UsersEntity = findUser.editUser(userRequestDto)
        return UsersDto.fromEntity(editedUser)
    }

    @Transactional
    fun deleteUser(userId: Long): UsersDto {
        val findUser: UsersEntity = findById(userId)
        usersRepository.delete(findUser)
        return UsersDto.fromEntity(findUser)
    }

    private fun findById(userId: Long): UsersEntity {
        return usersRepository.findByIdOrNull(userId) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun findUserByUserName(username: String): UsersEntity {
        return usersRepository.findByUserName(username) ?: throw BasicException(ErrorMessage.USER_NOT_FOUND)
    }

    private fun checkDuplicateUsername(username: String) {
        if (usersRepository.existsByUserName(username)) {
            throw BasicException(ErrorMessage.DUPLICATE_USER)
        }
    }
}