package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import javax.inject.Inject

class SetAuthenticatedUsecase @Inject constructor(
    private val localRepo: ILocalRepo
) {

    suspend fun invoke(isAuthenticated: Boolean) {
        return localRepo.setAuthenticated(isAuthenticated)
    }
}