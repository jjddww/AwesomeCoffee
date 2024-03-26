package com.jjddww.awesomecoffee.compose

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.jjddww.awesomecoffee.AppNavController
import com.jjddww.awesomecoffee.compose.order.DetailScreen
import com.jjddww.awesomecoffee.compose.order.PaymentScreen
import com.jjddww.awesomecoffee.compose.order.PaymentSuccessScreen
import com.jjddww.awesomecoffee.compose.order.SearchScreen
import com.jjddww.awesomecoffee.compose.order.SingleMenuPaymentScreen
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.rememberAppNavController
import com.jjddww.awesomecoffee.viewmodels.CouponViewModel
import com.jjddww.awesomecoffee.viewmodels.DetailViewModel
import com.jjddww.awesomecoffee.viewmodels.OrderViewModel
import com.jjddww.awesomecoffee.viewmodels.PaymentViewModel
import com.jjddww.awesomecoffee.viewmodels.SearchViewModel
import com.jjddww.awesomecoffee.viewmodels.SingleMenuPaymentViewModel

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
            onPaymentScreen = awesomeCoffeeNavController::navigateToPayment,
            onPaymentSuccessScreen = awesomeCoffeeNavController::navigateToSuccessPayment,
            onPaymentSingleScreen = awesomeCoffeeNavController::navigateToPaymentSingle,
            onHomeScreen = awesomeCoffeeNavController::navigateToHome,
            onNavigateRoute = awesomeCoffeeNavController::navigateToBottomBarRoute)
    }
}


private fun NavGraphBuilder.awesomeCoffeeNavGraph(
    couponViewModel: CouponViewModel,
    orderViewModel: OrderViewModel,
    navController: AppNavController,
    onMenuSelected: (Int, NavBackStackEntry) -> Unit,
    onSearchScreen: (NavBackStackEntry) -> Unit,
    onPaymentScreen: (NavBackStackEntry) -> Unit,
    onPaymentSingleScreen: (String, Int, String, Boolean, NavBackStackEntry) -> Unit,
    onPaymentSuccessScreen: (NavBackStackEntry)-> Unit,
    onHomeScreen: (NavBackStackEntry) -> Unit,
    onNavigateRoute: (String) -> Unit
){

    val searchViewModel = SearchViewModel()

    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = Sections.HOME.route
    ){
        AppNavGraph(couponViewModel, orderViewModel, navController, onMenuSelected, onSearchScreen, onPaymentScreen, onNavigateRoute)
    }

    composable(route = "${MainDestinations.DETAIL_ROUTE}/{${MainDestinations.MENU_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.MENU_ID_KEY) {type = NavType.IntType})

    ){navBackStackEntry ->
        val arguments = requireNotNull(navBackStackEntry.arguments)
        val menuId = arguments.getInt(MainDestinations.MENU_ID_KEY)

        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val viewModel: DetailViewModel = viewModel(
                it,
                "DetailViewModel",
                DetailViewModelFactory(LocalContext.current.applicationContext as Application, menuId)
            )
            DetailScreen(viewModel){menu, amount, isShot, option ->
                onPaymentSingleScreen(menu, amount, isShot, option, navBackStackEntry)}
        }
    }



    composable(route = MainDestinations.SEARCH_ROUTE){ navBackStackEntry ->
        SearchScreen(viewModel = searchViewModel,
            onBackButtonPressed = {
                searchViewModel.clear()
                navController.navigateUp() },
            onMenuSelected = {id -> onMenuSelected(id, navBackStackEntry)})
    }



    composable(route = MainDestinations.PAYMENT_ROUTE){navBackStackEntry ->
        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val viewModel: PaymentViewModel = viewModel(
                it,
                "PaymentViewModel",
                PaymentViewModelFactory(LocalContext.current.applicationContext as Application)
            )

            PaymentScreen(viewModel) { onPaymentSuccessScreen(navBackStackEntry) }
        }
    }


    
    composable(route = "${MainDestinations.PAYMENT_SINGLE_ROUTE}/{menu}/{amount}/{option}/{isShot}",
        arguments = listOf(navArgument("menu") {type = NavType.StringType},
            navArgument("amount") {type = NavType.IntType},
            navArgument("option") {type = NavType.StringType},
            navArgument("isShot") {type = NavType.BoolType}
        ))
    {navBackStackEntry ->
        val arguments = requireNotNull(navBackStackEntry.arguments)
        val menu = arguments.getString("menu")
        val menuData = Gson().fromJson(menu, Menu::class.java)

        val amount = arguments.getInt("amount")
        val option = arguments.getString("option") ?: ""
        val isShot = arguments.getBoolean("isShot")

        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val viewModel: SingleMenuPaymentViewModel = viewModel(
                it,
                "SinglePaymentViewModel",
                SinglePaymentViewModelFactory(LocalContext.current.applicationContext as Application)
            )
            SingleMenuPaymentScreen(viewModel =viewModel,
                onPaymentSuccessScreen = { onPaymentSuccessScreen(navBackStackEntry) },
                menu = menuData,
                option = option,
                amount = amount,
                isShot = isShot)
        }

    }

    composable(route = MainDestinations.PAYMENT_SUCCESS_ROUTE){navBackStackEntry ->
        PaymentSuccessScreen{ onHomeScreen(navBackStackEntry) }
    }
}

class DetailViewModelFactory(private val application: Application, private val menuId: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DetailViewModel(application, menuId) as T
    }
}

class PaymentViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PaymentViewModel(application) as T
    }
}

class SinglePaymentViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SingleMenuPaymentViewModel(application) as T
    }
}