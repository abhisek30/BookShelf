package com.abhisek.project.bookshelf.ui.auth.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Welcome to BookShelf \n to continue please Sign-In or Sign-Up",
                textAlign = TextAlign.Center
            )
            Button(onClick = { onNavigateToSignIn() }, modifier = Modifier.padding(16.dp)) {
                Text(text = "Sign-In")
            }
            Button(onClick = { onNavigateToSignUp() }) {
                Text(text = "Sign-Up")
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    BookShelfTheme {
        DashboardScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            onNavigateToSignIn = {

            }, onNavigateToSignUp = {

            })
    }
}