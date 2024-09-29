package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.domain.repository.ILocalRepo
import javax.inject.Inject

class AddUserToDbUsecase @Inject constructor(
    private val localRepo: ILocalRepo
) {

    suspend fun invoke(user: User) {
        return localRepo.saveUser(user)
    }
}