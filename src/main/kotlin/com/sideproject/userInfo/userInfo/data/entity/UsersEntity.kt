package com.sideproject.userInfo.userInfo.data.entity

import com.sideproject.userInfo.userInfo.data.dto.UserRequestDto
import jakarta.persistence.*


@Entity
@Table(name = "USERS")
class UsersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "user_name", unique = true)
    var userName: String,
    @Column(name = "nick_name")
    var nickName: String,
    var gender: String,
    @Column(name = "is_active")
    var isActive: Boolean,
    var type: String,
    var description: String?,
) : BasicEntity() {

    fun editUser(userRequestDto: UserRequestDto):UsersEntity {
        this.nickName = userRequestDto.userData.nickName
        this.gender = userRequestDto.userData.gender
        this.isActive = userRequestDto.userData.isActive
        this.type = userRequestDto.userData.type
        this.description = userRequestDto.userData.description
        return this
    }


    companion object {
        fun fromDto(userRequestDto: UserRequestDto): UsersEntity {
            return UsersEntity(
                userName = userRequestDto.userData.username,
                nickName = userRequestDto.userData.nickName,
                gender = userRequestDto.userData.gender,
                isActive = userRequestDto.userData.isActive,
                type = userRequestDto.userData.type,
                description = userRequestDto.userData.description
            )
        }
    }
}
