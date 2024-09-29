package com.abhisek.project.bookshelf.ui.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.project.bookshelf.domain.models.User
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is SignInEffect.NavigateToDashboard -> onNavigateToDashboard()
                is SignInEffect.NavigateToSignUp -> onNavigateToSignUp()
            }
        }.collect()
    }

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome Back!,")
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = {
                Text(text = "Email")
            },
            isError = uiState.value.emailError.isNotBlank(),
            supportingText = {
                if (uiState.value.emailError.isNotBlank()) {
                    Text(text = uiState.value.emailError, color = Color.Red)
                }
            }
        )
        OutlinedTextField(value = password.value, onValueChange = {
            password.value = it
        }, label = {
            Text(text = "Password")
        },
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.value.passwordError.isNotBlank(),
            supportingText = {
                if (uiState.value.passwordError.isNotBlank()) {
                    Text(text = uiState.value.passwordError, color = Color.Red)
                }
            }
        )
        Button(onClick = {
            if (email.value.isNotBlank() && password.value.isNotBlank()) {
                viewModel.validateUser(User(email.value, password.value))
            }
        }) {
            Text(text = "Sign-In")
        }
        Text(
            text = "First time using the app? Please Sign-Up",
            style = TextStyle.Default,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    onNavigateToSignUp()
                })
    }
}

@Preview
@Composable
private fun SignInScreenPreview() {
    BookShelfTheme {
        SignInScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            {}, {}
        )
    }
}