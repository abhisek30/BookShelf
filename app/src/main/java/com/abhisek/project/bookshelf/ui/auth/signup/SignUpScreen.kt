package com.abhisek.project.bookshelf.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
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
        )
        OutlinedTextField(value = password.value, onValueChange = {
            password.value = it
        }, label = {
            Text(text = "Password")
        },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign-Up")
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
                .background(Color.White)
        )
    }
}