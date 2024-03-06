package com.jjddww.awesomecoffee.compose.coupon

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.compose.AppBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CouponScreen(navController: NavController, onNavigateRoute: (String) -> Unit){
    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute) }){
        Text(text="쿠폰화면")
    }
}