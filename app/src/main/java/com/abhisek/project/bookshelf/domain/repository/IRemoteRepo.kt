package com.abhisek.project.bookshelf.domain.repository

import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.domain.models.Country

interface IRemoteRepo {

    suspend fun getCountries() : List<Country>

    suspend fun getCurrentCountry() : Country?

    suspend fun getBooks() : List<Book>

}