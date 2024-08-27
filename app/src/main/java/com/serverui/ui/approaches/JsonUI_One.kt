package com.serverui.ui.approaches

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Composable
fun Json_One() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleScope = lifecycleOwner.lifecycleScope

    var data: RemoteModel? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        data = Gson().fromJson(
            jsonStr(),
            RemoteModel::class.java
        )
    }
    data?.let {
        setView(
            data = it.data!!,
            applicationContext = context,
            lifecycleScope = lifecycleScope
        )
    }
}


@Composable
fun setView(
    data: ArrayList<Data>,
    applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    data.forEach { value ->
        checkUiType(value, applicationContext, lifecycleScope)
    }
}

@Composable
private fun checkUiType(
    value: Data,
    applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    when (value.type) {
        Type.SCAFFOLD -> showScaffold(value = value, applicationContext, lifecycleScope)
        Type.TEXT -> showText(value = value, applicationContext, lifecycleScope)
        Type.APP_BAR -> showAppBar(value = value, applicationContext, lifecycleScope)
        Type.IMAGE -> ImageView(value = value, applicationContext, lifecycleScope)
        Type.VERTICAL_LIST -> verticalList(
            value = value,
            applicationContext,
            lifecycleScope
        )
        Type.HORIZONTAL_LIST -> horizontalList(
            value = value,
            applicationContext,
            lifecycleScope
        )
        Type.ROW -> rowView(value = value, applicationContext, lifecycleScope)
        Type.COLUMN -> columnView(value = value, applicationContext, lifecycleScope)
        Type.UNKNOWN -> Spacer(modifier = Modifier.requiredSize(0.dp))
    }
}

@Composable
private fun showError() {
    Text(text = "There is a Problem")
}

@Composable
private fun showLoading() {
    Text(text = "w8 to load")
}

@Composable
private fun showText(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    Text(
        text = "${value.value}",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun showAppBar(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    TopAppBar(
        title = {
            setView(
                data = value.children,
                applicationContext = applicationContext,
                lifecycleScope
            )
        }
    )
}

@Composable
private fun showScaffold(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    Scaffold(
        topBar = {
            setView(value.topBar, applicationContext, lifecycleScope)
        }
    ) {
        it
        setView(
            data = value.children,
            applicationContext = applicationContext,
            lifecycleScope
        )
    }
}

@Composable
private fun verticalList(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            setView(
                data = value.children,
                applicationContext = applicationContext,
                lifecycleScope
            )
        }
    }
}

@Composable
private fun horizontalList(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            setView(
                data = value.children,
                applicationContext = applicationContext,
                lifecycleScope
            )
        }
    }
}

@Composable
private fun rowView(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        setView(
            data = value.children,
            applicationContext = applicationContext,
            lifecycleScope
        )
    }
}

@Composable
private fun columnView(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    Column {
        setView(
            data = value.children,
            applicationContext = applicationContext,
            lifecycleScope
        )
    }
}

@Composable
private fun ImageView(
    value: Data, applicationContext: Context,
    lifecycleScope: LifecycleCoroutineScope
) {
    AsyncImage(
        model = value.value,
        contentDescription = null,
        modifier = Modifier
            .size(56.dp)
            .clickable {

            }
    )
}


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }


fun jsonStr(): String {
    return "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"type\": \"SCAFFOLD\",\n" +
            "      \"top_bar\": [\n" +
            "        {\n" +
            "          \"type\": \"APP_BAR\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"type\": \"TEXT\",\n" +
            "              \"value\": \"Server Driven Ui\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"type\": \"VERTICAL_LIST\",\n" +
            "          \"children\": [\n" +
            "            {\n" +
            "              \"type\": \"ROW\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"type\": \"TEXT\",\n" +
            "                  \"value\": \"Hi 1\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"IMAGE\",\n" +
            "                  \"value\": \"https://8pic.ir/uploads/flowers.png\"\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              \"type\": \"ROW\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"type\": \"TEXT\",\n" +
            "                  \"value\": \"Hi 2\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"IMAGE\",\n" +
            "                  \"value\": \"https://picsum.photos/200/300\"\n" +
            "                }\n" +
            "              ]\n" +
            "            },\n" +
            "            {\n" +
            "              \"type\": \"ROW\",\n" +
            "              \"children\": [\n" +
            "                {\n" +
            "                  \"type\": \"TEXT\",\n" +
            "                  \"value\": \"Hi 3\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"IMAGE\",\n" +
            "                  \"value\": \"https://picsum.photos/seed/picsum/200/300\"\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}"
}

class RemoteModel {
    @SerializedName("data")
    val data: ArrayList<Data> = ArrayList()
}

data class Data(
    @SerializedName("children") val children: ArrayList<Data> = ArrayList(),
    @SerializedName("top_bar") val topBar: ArrayList<Data> = ArrayList(),
    @SerializedName("type") var type: Type = Type.UNKNOWN,
    @SerializedName("value") var value: String = "",
    @SerializedName("size") val size: Int = 0,
)

enum class Type {
    SCAFFOLD,
    TEXT,
    APP_BAR,
    IMAGE,
    VERTICAL_LIST,
    HORIZONTAL_LIST,
    ROW,
    COLUMN,
    UNKNOWN
}