package com.abhisek.project.bookshelf.domain.repository

interface ILocalRepo {

    suspend fun isAuthenticated() : Boolean?

    suspend fun setAuthenticated(isAuthenticated : Boolean)

}