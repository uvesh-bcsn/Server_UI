package com.serverui.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.serverui.model.home_sample.DashboardContentView
import com.serverui.ui.approaches.JsonUI_Three
import com.serverui.util.getJsonFromAssets

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@Composable
fun RootScreen(navController: NavHostController) {
    //Json_One()
    //Json_Two()
    Json_Three(navController)
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalFoundationApi
@Composable
fun Json_Three(navController: NavHostController) {
    val context = LocalContext.current
    var data: DashboardContentView? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        data = Gson().fromJson(
            getJsonFromAssets(context, "home_nav2.json"),
            DashboardContentView::class.java
        )
    }
    data?.let {
        JsonUI_Three(it, navController)
    }
}