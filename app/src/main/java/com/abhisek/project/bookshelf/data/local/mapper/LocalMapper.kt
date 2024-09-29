package com.abhisek.project.bookshelf.data.local.mapper

import com.abhisek.project.bookshelf.data.local.entity.UserEntity
import com.abhisek.project.bookshelf.domain.models.User

object LocalMapper {

    fun UserEntity.toDomain() : User {
        return User(
            mailId = this.mailId,
            password = this.password,
        )
    }

    fun User.toEntity() : UserEntity {
        return UserEntity(
            mailId = this.mailId,
            password = this.password,
        )
    }
}