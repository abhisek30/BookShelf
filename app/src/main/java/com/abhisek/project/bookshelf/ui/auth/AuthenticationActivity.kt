package com.abhisek.project.bookshelf.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abhisek.project.bookshelf.ui.dashboard.DashboardActivity
import com.abhisek.project.bookshelf.ui.theme.BookShelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookShelfTheme {
                AuthenticationNavHost(navigateToDashboard = {
                    startActivity(Intent(this@AuthenticationActivity, DashboardActivity::class.java))
                    finish()
                })
            }
        }
    }
}