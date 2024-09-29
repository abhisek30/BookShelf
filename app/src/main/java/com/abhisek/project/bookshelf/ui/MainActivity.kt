package com.abhisek.project.bookshelf.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.abhisek.project.bookshelf.ui.auth.AuthenticationActivity
import com.abhisek.project.bookshelf.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                viewModel.uiEffect.onEach { mainEffect ->
                    when(mainEffect) {
                        is MainEffect.NavigateToDashboard -> {
                            startActivity(Intent(this@MainActivity,DashboardActivity::class.java))
                            finish()
                        }
                        is MainEffect.NavigateToAuthentication -> {
                            startActivity(Intent(this@MainActivity,AuthenticationActivity::class.java))
                            finish()
                        }
                    }
                }.collect()
            }
        }
    }
}