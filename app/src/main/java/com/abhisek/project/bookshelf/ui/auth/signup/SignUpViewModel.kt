package com.abhisek.project.bookshelf.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.domain.usecases.AddUserToDbUsecase
import com.abhisek.project.bookshelf.domain.usecases.FetchUsersByMailIdUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val fetchUsersByMailIdUsecase: FetchUsersByMailIdUsecase,
    private val addUserToDbUsecase: AddUserToDbUsecase,
) : ViewModel()  {

    init {
    }

    private fun fetchUsersByMailId(mailId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchUsersByMailIdUsecase.invoke(mailId)
        }
    }

    private fun addUserToDb(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserToDbUsecase.invoke(user)
            fetchUsersByMailId(user.mailId)
        }
    }
}