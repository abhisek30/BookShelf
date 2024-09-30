package com.abhisek.project.bookshelf.data.remote.mapper

import com.abhisek.project.bookshelf.data.remote.dto.BookDto
import com.abhisek.project.bookshelf.data.remote.dto.CountryDto
import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.domain.models.Country


object RemoteMapper {
    fun List<CountryDto>?.toDomain() = this?.let { countryDto ->
        countryDto.map { Country(it.country.orEmpty(), it.region.orEmpty()) }
    } ?: run {
        emptyList()
    }

    fun CountryDto?.toDomain() = this?.let {
        Country(it.country.orEmpty(), it.region.orEmpty())
    } ?: kotlin.run {
        null
    }

    fun List<BookDto>?.toBookListDomain() = this?.let { bookDto ->
        bookDto.map {
            Book(
                id = it.id,
                image = it.image,
                popularity = it.popularity,
                publishedChapterDate = it.publishedChapterDate,
                score = it.score,
                title = it.title
            )
        }
    }
}