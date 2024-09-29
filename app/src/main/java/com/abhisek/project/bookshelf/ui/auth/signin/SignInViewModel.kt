package com.abhisek.project.bookshelf.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.domain.usecases.FetchUsersByMailIdUsecase
import com.abhisek.project.bookshelf.domain.usecases.SetAuthenticatedUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val fetchUsersByMailIdUsecase: FetchUsersByMailIdUsecase,
    private val setAuthenticatedUsecase: SetAuthenticatedUsecase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<SignInEffect>()
    val uiEffect: SharedFlow<SignInEffect> = _uiEffect

    fun validateUser(user: User) {
        _uiState.value = uiState.value.copy(
            emailError = "",
            passwordError = "",
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchUsersByMailIdUsecase.invoke(user.mailId)
            result?.let {
                if(user.password == result.password) {
                    signInUser()
                } else {
                    _uiState.value = uiState.value.copy(
                        passwordError = "Password didn't match, please try again"
                    )
                }
            } ?: run {
                _uiState.value = uiState.value.copy(
                    emailError = "Email Doesn't Exists, Please Try to Sign-Un"
                )
            }
        }
    }

    private fun signInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            setAuthenticatedUsecase.invoke(true)
            _uiEffect.emit(SignInEffect.NavigateToDashboard)
        }
    }
}

data class SignInUiState(
    val emailError: String = "",
    val passwordError : String = "",
)

sealed class SignInEffect {
    data object NavigateToDashboard : SignInEffect()

    data object NavigateToSignUp : SignInEffect()
}