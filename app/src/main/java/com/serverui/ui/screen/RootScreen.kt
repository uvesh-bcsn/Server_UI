package com.serverui.ui.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.serverui.model.home_sample.DashboardContentView
import com.serverui.model.home_sample.HomeData
import com.serverui.model.json_two.ComponentData
import com.serverui.ui.approaches.JsonUI_Three
import com.serverui.ui.approaches.JsonUI_Two
import com.serverui.ui.approaches.RemoteModel
import com.serverui.ui.approaches.jsonStr
import com.serverui.ui.approaches.jsonStr2
import com.serverui.ui.approaches.setView
import com.serverui.util.getJsonFromAssets
import java.io.IOException

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@Composable
fun RootScreen() {
    //Json_One()
    //Json_Two()
    Json_Three()
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalFoundationApi
@Composable
fun Json_Three() {
    val context = LocalContext.current
    var data: DashboardContentView? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        data = Gson().fromJson(
            getJsonFromAssets(context, "home_sample.json"),
            DashboardContentView::class.java
        )
    }
    data?.let {
        JsonUI_Three(it)
    }
}