package com.abhisek.project.bookshelf.domain.usecases

import com.abhisek.project.bookshelf.domain.models.Country
import com.abhisek.project.bookshelf.domain.repository.IRemoteRepo
import javax.inject.Inject

class FetchCountriesUsecase @Inject constructor(
    private val remoteRepo: IRemoteRepo
) {
    suspend fun invoke() : List<Country> {
        return remoteRepo.getCountries()
    }
}