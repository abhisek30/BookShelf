package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.domain.repository.IRemoteRepo
import javax.inject.Inject

class FetchBooksUsecase @Inject constructor(
    private val remoteRepo: IRemoteRepo
) {
    suspend fun invoke() : List<Book> {
        return remoteRepo.getBooks()
    }
}