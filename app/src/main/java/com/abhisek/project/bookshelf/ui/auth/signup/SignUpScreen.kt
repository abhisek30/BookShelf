package com.abhisek.project.bookshelf.ui.auth.signup

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
import com.abhisek.project.bookshelf.ui.auth.util.validatePassword
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onNavigateToDashboard: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.onEach { effect ->
            when (effect) {
                is SignUpEffect.NavigateToDashboard -> onNavigateToDashboard()
                is SignUpEffect.NavigateToSignIn -> onNavigateToSignIn()
            }
        }.collect()
    }

    uiState.value.countries?.let {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val passwordError = remember { mutableStateOf("") }
        Column(
            modifier = modifier, verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Let's create your account")
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                label = {
                    Text(text = "Email")
                },
                isError = uiState.value.error.isNotBlank(),
                supportingText = {
                    if (uiState.value.error.isNotBlank()) {
                        Text(text = uiState.value.error, color = Color.Red)
                    }
                }
            )
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
                passwordError.value = validatePassword(password.value)
            }, label = {
                Text(text = "Password")
            },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError.value.isNotBlank(),
                supportingText = {
                    if (passwordError.value.isNotBlank()) {
                        Text(text = passwordError.value, color = Color.Red)
                    }
                }
            )
            Button(onClick = {
                if (passwordError.value.isBlank()) {
                    viewModel.validateUser(User(email.value, password.value))
                }
            }) {
                Text(text = "Sign-Up")
            }
            Text(
                text = "Existing User? Please Sign-In",
                style = TextStyle.Default,
                modifier = Modifier.padding(top = 8.dp).clickable {
                    onNavigateToSignIn()
                })
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    BookShelfTheme {
        SignUpScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            {}, {}
        )
    }
}