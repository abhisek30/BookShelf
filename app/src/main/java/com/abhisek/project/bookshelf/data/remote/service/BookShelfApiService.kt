package com.abhisek.project.bookshelf.data.remote.service

import com.abhisek.project.bookshelf.data.remote.dto.BookDto
import com.abhisek.project.bookshelf.data.remote.dto.CountryDto
import retrofit2.Response
import retrofit2.http.GET

interface BookShelfApiService {

    @GET("b/IU1K")
    suspend fun getCountries(): Response<List<CountryDto>>

    @GET("http://ip-api.com/json")
    suspend fun getDefaultCountry(): Response<CountryDto>

    @GET("b/CNGI")
    suspend fun getBooks() : Response<List<BookDto>>

}