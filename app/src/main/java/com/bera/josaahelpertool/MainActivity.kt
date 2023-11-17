package com.bera.josaahelpertool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bera.josaahelpertool.navigation.Navigation
import com.bera.josaahelpertool.network.connectivity.ConnectivityObserver
import com.bera.josaahelpertool.network.connectivity.ConnectivityStatus
import com.bera.josaahelpertool.screens.home.NetworkErrorScreen
import com.bera.josaahelpertool.ui.theme.CollegeSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            val cacheDir: File = applicationContext.cacheDir
                            if (cacheDir.exists()) {
                                val httpCacheDir = File(cacheDir, "http-cache")
                                if (httpCacheDir.exists()) {
                                    Navigation()
                                } else {
                                    NetworkErrorScreen(isVisible = true)
                                }
                            } else {
                                NetworkErrorScreen(isVisible = true)
                            }
                        }
                    }
                }
            }
        }
    }
}