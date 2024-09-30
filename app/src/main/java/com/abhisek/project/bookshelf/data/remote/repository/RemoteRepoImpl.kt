package com.abhisek.project.bookshelf.data.remote.repository

import com.abhisek.project.bookshelf.data.remote.mapper.RemoteMapper.toBookListDomain
import com.abhisek.project.bookshelf.data.remote.mapper.RemoteMapper.toDomain
import com.abhisek.project.bookshelf.data.remote.service.BookShelfApiService
import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.domain.models.Country
import com.abhisek.project.bookshelf.domain.repository.IRemoteRepo
import javax.inject.Inject

class RemoteRepoImpl @Inject constructor(
    private val bookShelfApiService: BookShelfApiService,
) : IRemoteRepo {
    override suspend fun getCountries(): List<Country> {
        val response = bookShelfApiService.getCountries()
        return if (response.isSuccessful) {
            response.body()?.toDomain().orEmpty()
        } else {
            emptyList()
        }
    }

    override suspend fun getCurrentCountry(): Country? {
        val response = bookShelfApiService.getDefaultCountry()
        return if (response.isSuccessful) {
            response.body()?.toDomain()
        } else {
            null
        }
    }

    override suspend fun getBooks(): List<Book> {
        val response = bookShelfApiService.getBooks()
        return if (response.isSuccessful) {
            response.body()?.toBookListDomain().orEmpty()
        } else {
            emptyList()
        }
    }
}