package com.serverui.model.home_sample

@kotlinx.serialization.Serializable
data class DashboardContentView(
    val type: String,
    val data: DashboardData,
    val children: List<WidgetView>
)

@kotlinx.serialization.Serializable
data class DashboardData(
    val bgcolor: List<String>
)

@kotlinx.serialization.Serializable
data class WidgetView(
    val type: String,
    val data: WidgetData? = null,
    val children: List<WidgetView>? = null
)

@kotlinx.serialization.Serializable
data class WidgetData(
    val text: String? = null,
    val balance: String? = null,
    val image: String? = null,
    val bgcolor: List<String>? = null,
    val bannerArray: List<Banner>? = null
)

@kotlinx.serialization.Serializable
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

