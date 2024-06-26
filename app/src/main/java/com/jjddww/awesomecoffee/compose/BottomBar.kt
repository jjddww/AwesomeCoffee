package com.jjddww.awesomecoffee.compose

import android.app.Activity
import android.app.Application
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.jjddww.awesomecoffee.AppNavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.coupon.CouponScreen
import com.jjddww.awesomecoffee.compose.other.OtherScreen
import com.jjddww.awesomecoffee.compose.home.HomeScreen
import com.jjddww.awesomecoffee.compose.login.LoginScreen
import com.jjddww.awesomecoffee.compose.order.OrderScreen
import com.jjddww.awesomecoffee.compose.payment.CartScreen
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.ui.theme.onPrimaryDark
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLightMediumContrast
import com.jjddww.awesomecoffee.viewmodels.CartViewModel
import com.jjddww.awesomecoffee.viewmodels.CouponViewModel
import com.jjddww.awesomecoffee.viewmodels.DetailViewModel
import com.jjddww.awesomecoffee.viewmodels.LoginViewModel
import com.jjddww.awesomecoffee.viewmodels.OrderViewModel

enum class Sections(
    @StringRes val title:Int,
    @DrawableRes val icon: Int,
    val route: String
){
    CART(R.string.cart, R.drawable.baseline_shopping_cart_24, "home/cart"),
    ORDER(R.string.order, R.drawable.baseline_coffee_24, "home/order"),
    HOME(R.string.home, R.drawable.baseline_home_24, "home/main"),
    COUPON(R.string.coupon, R.drawable.baseline_local_activity_24, "home/coupon"),
    OTHER(R.string.other, R.drawable.baseline_more_horiz_24, "home/other")

}

object Login{
    const val LOGIN_ROUTE = "home/login"
}


object MainDestinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val SEARCH_ROUTE = "search"
    const val PAYMENT_ROUTE = "payment"
    const val PAYMENT_SINGLE_ROUTE = "payment_single"
    const val PAYMENT_SUCCESS_ROUTE = "payment_success"
    const val ORDER_HISTORY_ROUTE = "order_history"
    const val MENU_ID_KEY = "menuId"
}


class CartViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CartViewModel(application) as T
    }
}

fun NavGraphBuilder.AppNavGraph(
    couponViewModel: CouponViewModel,
    viewModel: OrderViewModel,
    appNavController: AppNavController,
    onMenuSelected: (Int, NavBackStackEntry) -> Unit,
    onSearchScreen: (NavBackStackEntry) -> Unit,
    onPaymentScreen: (NavBackStackEntry) -> Unit,
    onHomeScreen: (NavBackStackEntry) -> Unit,
    onLoginScreen: (NavBackStackEntry) -> Unit,
    onOrderHistoryScreen:(NavBackStackEntry) -> Unit,
    onNavigateRoute: (String) -> Unit)
{
        composable(Sections.CART.route){navBackStackEntry ->
            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                val viewModel: CartViewModel = viewModel(
                    it,
                    "DetailViewModel",
                    CartViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                CartScreen(
                    viewModel= viewModel,
                    navController = appNavController.navController,
                    onNavigateRoute = onNavigateRoute,
                    onPaymentScreen = {onPaymentScreen(navBackStackEntry)})
            }
        }
        composable(Sections.ORDER.route){navBackStackEntry ->
            OrderScreen(
                viewModel = viewModel,
                navController = appNavController.navController,
                onNavigateRoute = onNavigateRoute,
                onMenuSelected = {id -> onMenuSelected(id, navBackStackEntry)},
                onSearchScreen = {onSearchScreen(navBackStackEntry)}
            )
        }
        composable(Sections.HOME.route){navBackStackEntry ->
            HomeScreen(navController = appNavController.navController, onNavigateRoute = onNavigateRoute,
            ) { id -> onMenuSelected(id, navBackStackEntry) }
        }
        composable(Sections.COUPON.route){
            CouponScreen(couponViewModel, appNavController.navController, onNavigateRoute)
        }
        composable(Sections.OTHER.route){navBackStackEntry ->
            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                val viewModel: LoginViewModel = viewModel(
                    it,
                    "LoginViewModel",
                    LoginViewModelFactory(LocalContext.current as Activity)
                )

                OtherScreen(appNavController.navController, onNavigateRoute, viewModel, { onLoginScreen(navBackStackEntry) }) {
                    onOrderHistoryScreen(
                        navBackStackEntry
                    )
                }
            }
        }

        composable(route = Login.LOGIN_ROUTE){ navBackStackEntry ->

            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                val viewModel: LoginViewModel = viewModel(
                    it,
                    "LoginViewModel",
                    LoginViewModelFactory(LocalContext.current as Activity)
                )

            LoginScreen(viewModel = viewModel){onHomeScreen(navBackStackEntry)}
        }

    }

}


@Composable
fun AppBottomBar(
    navController: NavController,
    onNavigateRoute: (String) -> Unit
){
    val items = Sections.entries.toTypedArray()

    NavigationBar (modifier = Modifier
        .height(80.dp)
        .fillMaxWidth(),
        containerColor = surfaceVariantLightMediumContrast
    ){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = surfaceVariantLightMediumContrast,
                    selectedIconColor = onPrimaryDark,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = onPrimaryDark,
                    unselectedTextColor = Color.Gray
                ),
                onClick = {
                    onNavigateRoute(item.route)

                },
                icon = {
                    Icon(modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(id = item.icon),
                        contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.title),
                        fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun AppBarPreview(){
    AppBottomBar(navController = NavHostController(LocalContext.current),
        {})
}