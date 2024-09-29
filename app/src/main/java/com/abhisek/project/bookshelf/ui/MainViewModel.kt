package com.abhisek.project.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.project.bookshelf.domain.usecases.IsAuthenticatedUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isAuthenticatedUsecase: IsAuthenticatedUsecase,
) : ViewModel()  {

    private val _uiEffect = MutableSharedFlow<MainEffect>()
    val uiEffect : SharedFlow<MainEffect> = _uiEffect

    init {
        checkIsAuthenticated()
    }

    private fun checkIsAuthenticated() {
        viewModelScope.launch(Dispatchers.IO) {
            val isAuthenticated = isAuthenticatedUsecase.invoke()
            if(isAuthenticated) {
                _uiEffect.emit(MainEffect.NavigateToHome)
            } else {
                _uiEffect.emit(MainEffect.NavigateToAuthentication)
            }
        }
    }
}

sealed class MainEffect {
    data object NavigateToHome : MainEffect()

    data object NavigateToAuthentication : MainEffect()
}