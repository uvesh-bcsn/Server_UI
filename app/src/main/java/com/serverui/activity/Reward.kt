package com.serverui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.serverui.activity.ui.theme.ServerUITheme
import com.serverui.model.home_sample.Action
import com.serverui.ui.screen.Nav
import kotlinx.serialization.json.Json

class Reward : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServerUITheme {
                val action = intent.getStringExtra("action")



                val actionClass = Gson().fromJson(action, Action::class.java)
                val list = listOf(
                    Nav("Task", Icons.Rounded.ShoppingCart),
                    Nav("Reward", Icons.Rounded.Edit),
                    Nav("Airdrop", Icons.Rounded.Create)
                )
                var selected by remember {
                    mutableStateOf(actionClass.screen)
                }
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            for (nav in list) {
                                NavigationBarItem(
                                    selected = selected == nav.title,
                                    onClick = { selected = nav.title },
                                    icon = {
                                        Icon(
                                            imageVector = nav.icon,
                                            contentDescription = null
                                        )
                                    }, label = {
                                        Text(text = nav.title)
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(text = actionClass.args.toString(), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}