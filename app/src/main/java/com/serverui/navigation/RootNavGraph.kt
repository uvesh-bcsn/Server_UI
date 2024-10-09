package com.serverui.navigation

import android.app.Activity
import android.content.Intent
import android.provider.DocumentsContract.Root
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.serverui.activity.Reward
import com.serverui.model.home_sample.Action
import com.serverui.ui.screen.BuyCrypto
import com.serverui.ui.screen.RootScreen
import kotlinx.serialization.Serializable
import kotlin.math.tan

const val DEEPLINK_DOMAIN = "ixfidomain.com"

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@ExperimentalFoundationApi
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.RootScreen
    ) {
        composable<Route.RootScreen> {
            RootScreen(navController = navController)
        }
        composable<Route.BuyCryptoData>(
            deepLinks = listOf(
                navDeepLink<Route.BuyCryptoData>(
                    basePath = "https://$DEEPLINK_DOMAIN"
                )
            )
        ) {
            val args = it.toRoute<Route.BuyCryptoData>()
            BuyCrypto(args, navController)
        }
    }
}


object Route {
    @Serializable
    object RootScreen

    @Serializable
    data class BuyCryptoData(val tab: String, val url: String)
}

fun getRoute(activity: Activity, action: Action, navController: NavHostController) {
    when (action.screen) {
        "BuyCrypto" -> {
            navController.navigate("https://$DEEPLINK_DOMAIN/History/DeeplinkNavigation".toUri())
        }

        "Reward" -> {
            val intent = Intent(activity, Reward::class.java).also { intent ->
                action.args?.let {
                    intent.putExtra("args", it.toString())
                }
                action.action?.let {
                    intent.putExtra("action", it.toString())
                }
            }
            activity.startActivity(intent)

        }
    }
}