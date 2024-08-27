package com.serverui.model.home_sample

data class BannerArray(
    val banner_url: String,
    val is_android: Boolean,
    val is_external_browser_redirect: Boolean,
    val is_ios: Boolean,
    val is_require_login: Boolean,
    val slug: String,
    val webview_title: String,
    val webview_url: String
)