package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import javax.inject.Inject

class FetchUsersByMailIdUsecase @Inject constructor(
    private val localRepo: ILocalRepo
) {

    suspend fun invoke(maildId: String) : User? {
        return localRepo.getUserByMailId(maildId)
    }
}