package com.abhisek.project.bookshelf.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.abhisek.project.bookshelf.ui.auth.AuthenticationActivity
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookShelfTheme {
                DashboardScreen(
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToAuthentication = {
                        startActivity(Intent(this@DashboardActivity, AuthenticationActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}