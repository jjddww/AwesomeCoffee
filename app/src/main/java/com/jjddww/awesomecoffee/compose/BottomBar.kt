package com.jjddww.awesomecoffee.compose

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.coupon.CouponScreen
import com.jjddww.awesomecoffee.compose.etc.EtcScreen
import com.jjddww.awesomecoffee.compose.home.HomeScreen
import com.jjddww.awesomecoffee.compose.order.OrderScreen
import com.jjddww.awesomecoffee.compose.payment.CartScreen
import com.jjddww.awesomecoffee.ui.theme.onPrimaryDark
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLightMediumContrast
import com.jjddww.awesomecoffee.viewmodels.HomeViewModel

enum class Sections(
    @StringRes val title:Int,
    @DrawableRes val icon: Int,
    val route: String
){
    CART(R.string.cart, R.drawable.baseline_shopping_cart_24, "cart"),
    ORDER(R.string.order, R.drawable.baseline_coffee_24, "order"),
    HOME(R.string.home, R.drawable.baseline_home_24, "home"),
    COUPON(R.string.coupon, R.drawable.baseline_local_activity_24, "coupon"),
    ETC(R.string.etc, R.drawable.baseline_more_horiz_24, "etc")

}

@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Sections.HOME.route){
        composable(Sections.CART.route){
            CartScreen()
        }
        composable(Sections.ORDER.route){
            OrderScreen()
        }
        composable(Sections.HOME.route){
            HomeScreen(HomeViewModel())
        }
        composable(Sections.COUPON.route){
            CouponScreen()
        }
        composable(Sections.ETC.route){
            EtcScreen()
        }
    }
}

@Composable
fun AppBottomBar(
    items: Array<Sections>,
    navController: NavHostController
){
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
                          navController.navigate(item.route){
                              popUpTo(navController.graph.findStartDestination().id){
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          }

                },
                icon = {
                    Icon(painter = painterResource(id = item.icon),contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.title),
                        modifier = Modifier.padding(top = 12.dp),
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
    AppBottomBar(items = Sections.values(),
        navController = NavHostController(LocalContext.current))
}