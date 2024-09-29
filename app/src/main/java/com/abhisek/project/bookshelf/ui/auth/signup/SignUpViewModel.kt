package com.abhisek.project.bookshelf.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.project.bookshelf.domain.models.Country
import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.domain.usecases.AddUserToDbUsecase
import com.abhisek.project.bookshelf.domain.usecases.FetchCountriesUsecase
import com.abhisek.project.bookshelf.domain.usecases.FetchCurrentCountryUsecase
import com.abhisek.project.bookshelf.domain.usecases.FetchUsersByMailIdUsecase
import com.abhisek.project.bookshelf.domain.usecases.SetAuthenticatedUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val fetchUsersByMailIdUsecase: FetchUsersByMailIdUsecase,
    private val addUserToDbUsecase: AddUserToDbUsecase,
    private val fetchCountriesUsecase: FetchCountriesUsecase,
    private val fetchCurrentCountryUsecase: FetchCurrentCountryUsecase,
    private val setAuthenticatedUsecase: SetAuthenticatedUsecase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<SignUpEffect>()
    val uiEffect: SharedFlow<SignUpEffect> = _uiEffect

    init {
        preFetchCountryData()
    }

    private fun preFetchCountryData() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfCountryApi = async { fetchCountriesUsecase.invoke() }
            val currentCountryApi = async { fetchCurrentCountryUsecase.invoke() }
            val listOfCountries = listOfCountryApi.await()
            val currentCountry = currentCountryApi.await()
            _uiState.value = uiState.value.copy(
                countries = listOfCountries,
                currentCountry = currentCountry
            )
        }
    }

    fun validateUser(user: User) {
        _uiState.value = uiState.value.copy(
            error = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchUsersByMailIdUsecase.invoke(user.mailId)
            result?.let {
                _uiState.value = uiState.value.copy(
                    error = "Email Already Exists, Please Try to Sign-In"
                )
            } ?: run {
                signUpUser(user)
            }
        }
    }

    private fun signUpUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserToDbUsecase.invoke(user)
            setAuthenticatedUsecase.invoke(true)
            _uiEffect.emit(SignUpEffect.NavigateToDashboard)
        }
    }
}

data class SignUpUiState(
    val countries: List<Country>? = null,
    val currentCountry: Country? = null,
    val error: String = ""
)

sealed class SignUpEffect {
    data object NavigateToDashboard : SignUpEffect()

    data object NavigateToSignIn : SignUpEffect()
}