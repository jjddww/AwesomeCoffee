package com.jjddww.awesomecoffee.compose

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.jjddww.awesomecoffee.compose.login.LoginScreen
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight

@Composable
fun AwesomeCoffeeApp(){
    AwesomeCoffeeTheme {
        Surface (color = surfaceVariantLight){
            LoginScreen()
        }
    }
}