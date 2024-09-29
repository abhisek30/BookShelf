package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import javax.inject.Inject

class IsAuthenticatedUsecase @Inject constructor(
    private val localRepo: ILocalRepo
) {

    suspend fun invoke() : Boolean {
        return localRepo.isAuthenticated() ?: false
    }
}