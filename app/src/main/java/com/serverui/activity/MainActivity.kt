package com.serverui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.serverui.navigation.RootNavGraph
import com.serverui.ui.screen.RootScreen
import com.serverui.ui.theme.ServerUITheme

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalLayoutApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServerUITheme {
                RootNavGraph()
            }
        }
    }
}