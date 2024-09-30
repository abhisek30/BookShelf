package com.abhisek.project.bookshelf.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.project.bookshelf.domain.models.Book
import com.abhisek.project.bookshelf.domain.usecases.FetchBooksUsecase
import com.abhisek.project.bookshelf.domain.usecases.SetAuthenticatedUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fetchBooksUsecase: FetchBooksUsecase,
    private val setAuthenticatedUsecase: SetAuthenticatedUsecase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<DashboardEffect>()
    val uiEffect: SharedFlow<DashboardEffect> = _uiEffect

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = fetchBooksUsecase.invoke()
            _uiState.value = uiState.value.copy(
                books = bookMapper(result)
            )
        }
    }

    private fun bookMapper(books: List<Book>): Map<Int, List<Book>> {
        return books.groupBy { book ->
            getYearFromPublishedDate(book.publishedChapterDate)
        }.toSortedMap(compareByDescending { it })
    }

    private fun getYearFromPublishedDate(date: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date.toLong() * 1000
        return calendar.get(Calendar.YEAR)
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            setAuthenticatedUsecase.invoke(false)
            _uiEffect.emit(DashboardEffect.NavigateToAuthentication)
        }
    }
}

data class DashboardUiState(
    val books: Map<Int,List<Book>>? = null,
)

sealed class DashboardEffect {
    data object NavigateToAuthentication : DashboardEffect()
}