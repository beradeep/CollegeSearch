package com.bera.collegesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bera.collegesearch.navigation.Navigation
import com.bera.collegesearch.network.connectivity.ConnectivityObserver
import com.bera.collegesearch.network.connectivity.ConnectivityStatus
import com.bera.collegesearch.screens.home.NetworkErrorScreen
import com.bera.collegesearch.ui.theme.CollegeSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = ConnectivityObserver(applicationContext)
        setContent {
            CollegeSearchTheme {

                val status by connectivityObserver.observe()
                    .collectAsState(initial = ConnectivityStatus.Unavailable)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    when(status) {
                        ConnectivityStatus.Available -> Navigation()
                        else -> {
                            var showError by remember {
                                mutableStateOf(false)
                            }
                            LaunchedEffect(Unit) {
                                withContext(Dispatchers.IO) {
                                    delay(2000)
                                    showError = true
                                }
                            }
                            NetworkErrorScreen(isVisible = showError)
                        }
                    }
                }
            }
        }
    }
}