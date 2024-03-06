package com.jjddww.awesomecoffee.compose.order

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreen(menuId: Int){
    Text(text = menuId.toString())
}
