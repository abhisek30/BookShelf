package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.models.Country
import com.abhisek.project.bookshelf.domain.repository.IRemoteRepo
import javax.inject.Inject

class FetchCurrentCountryUsecase @Inject constructor(
    private val remoteRepo: IRemoteRepo
) {

    suspend fun invoke() : Country? {
        return remoteRepo.getCurrentCountry()
    }
}