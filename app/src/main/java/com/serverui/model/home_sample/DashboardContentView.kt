package com.serverui.model.home_sample

import com.google.gson.JsonObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import org.json.JSONObject
import java.util.HashMap
import java.util.Objects

data class DashboardContentView(
    val type: String,
    val data: DashboardData,
    val children: List<WidgetView>
)

data class DashboardData(
    val image: String? = null,
    val bgcolor: List<String>
)

data class WidgetView(
    val type: String,
    val data: WidgetData? = null,
    val children: List<WidgetView>? = null
)

data class WidgetData(
    val text: String? = null,
    val balance: String? = null,
    val image: String? = null,
    val bgcolor: List<String>? = null,
    val bannerArray: List<Banner>? = null,
    val action: Action? = null
)

data class Action(
    val screen: String? = null,
    val args: Any? = null,
    val action: Any? = null
)

data class Banner(
    val banner_url: String,
    val slug: String,
    val webview_url: String,
    val is_require_login: Boolean,
    val webview_title: String,
    val is_external_browser_redirect: Boolean,
    val is_android: Boolean,
    val is_ios: Boolean
)

