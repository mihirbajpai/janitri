package com.example.janitri_mihirbajpai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.janitri_mihirbajpai.ui.HomeScreen
import com.example.janitri_mihirbajpai.ui.theme.JanitriMihirBajpaiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JanitriMihirBajpaiTheme {
                AppContainer()
            }
        }
    }
}

@Composable
fun AppContainer() {
    HomeScreen()
}
