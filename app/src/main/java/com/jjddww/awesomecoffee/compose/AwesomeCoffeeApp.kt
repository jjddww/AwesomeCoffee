package com.jjddww.awesomecoffee.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jjddww.awesomecoffee.AppNavController
import com.jjddww.awesomecoffee.compose.order.DetailScreen
import com.jjddww.awesomecoffee.compose.order.SearchScreen
import com.jjddww.awesomecoffee.rememberAppNavController
import com.jjddww.awesomecoffee.viewmodels.CouponViewModel
import com.jjddww.awesomecoffee.viewmodels.DetailViewModel
import com.jjddww.awesomecoffee.viewmodels.OrderViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AwesomeCoffeeApp() {
    val awesomeCoffeeNavController = rememberAppNavController()
    val orderViewModel = OrderViewModel()
    val couponViewModel = CouponViewModel()
    NavHost(
        navController = awesomeCoffeeNavController.navController,
        startDestination = MainDestinations.HOME_ROUTE){
        awesomeCoffeeNavGraph(
            couponViewModel = couponViewModel,
            orderViewModel = orderViewModel,
            navController = awesomeCoffeeNavController,
            onMenuSelected = awesomeCoffeeNavController::navigateToDetail,
            onSearchScreen = awesomeCoffeeNavController::navigateToSearch,
            onNavigateRoute = awesomeCoffeeNavController::navigateToBottomBarRoute)
    }
}


private fun NavGraphBuilder.awesomeCoffeeNavGraph(
    couponViewModel: CouponViewModel,
    orderViewModel: OrderViewModel,
    navController: AppNavController,
    onMenuSelected: (Int, NavBackStackEntry) -> Unit,
    onSearchScreen: (NavBackStackEntry) -> Unit,
    onNavigateRoute: (String) -> Unit
){
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ){
        AppNavGraph(couponViewModel, orderViewModel, navController, onMenuSelected, onSearchScreen, onNavigateRoute)
    }

    composable(route = "${MainDestinations.DETAIL_ROUTE}/{${MainDestinations.MENU_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.MENU_ID_KEY) {type = NavType.IntType})

    ){
        val arguments = requireNotNull(it.arguments)
        val menuId = arguments.getInt(MainDestinations.MENU_ID_KEY)
        DetailScreen(DetailViewModel(menuId))
    }

    composable(route = "${MainDestinations.SEARCH_ROUTE}"){navBackStackEntry ->
        SearchScreen(onMenuSelected = {id -> onMenuSelected(id, navBackStackEntry)})
    }
}