package com.abhisek.project.bookshelf.domain.repository

import com.abhisek.project.bookshelf.domain.models.User

interface ILocalRepo {

    suspend fun isAuthenticated() : Boolean?

    suspend fun setAuthenticated(isAuthenticated : Boolean)

    suspend fun getUserByMailId(mailId: String) : User?

    suspend fun getUsers() : List<User>

    suspend fun saveUser(user: User)

}