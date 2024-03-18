package com.jjddww.awesomecoffee

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jjddww.awesomecoffee.compose.MainDestinations
import com.jjddww.awesomecoffee.compose.Sections

@Composable
fun rememberAppNavController(
    navController: NavHostController = rememberNavController()
): AppNavController = remember(navController){
    AppNavController(navController)
}

@Stable
class AppNavController(
    val navController: NavHostController
){
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateToDetail(menuId: Int, from: NavBackStackEntry){
        if(from.lifecycle.currentState == Lifecycle.State.RESUMED){
            navController.navigate("${MainDestinations.DETAIL_ROUTE}/$menuId")
        }
    }


    fun navigateUp() {
        navController.navigateUp()
    }

    @SuppressLint("RestrictedApi")
    fun navigateToBottomBarRoute(route: String) {

        if (route != currentRoute) {
            navController.navigate(route){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        navController.addOnDestinationChangedListener { controller, _, _ ->
            val routes = controller
                .currentBackStack.value
                .map { it.destination.route }
                .joinToString(", ")

            Log.d("BackStackLog", "BackStack: $routes")
        }
    }
}