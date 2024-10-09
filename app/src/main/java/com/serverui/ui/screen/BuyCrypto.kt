package com.serverui.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.serverui.navigation.Route

@ExperimentalMaterial3Api
@Composable
fun BuyCrypto(
    tag: Route.BuyCryptoData,
    navController: NavHostController
) {
    val list = listOf(
        Nav("Buy", Icons.Rounded.ShoppingCart),
        Nav("Sell", Icons.Rounded.Edit),
        Nav("History", Icons.Rounded.Create)
    )
    var selected by remember {
        mutableStateOf(tag.tab)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {  }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                }
            })
        },
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
        }
    ) {
        Text(text = tag.url, Modifier.padding(it))
    }
}

data class Nav(val title: String, val icon: ImageVector)