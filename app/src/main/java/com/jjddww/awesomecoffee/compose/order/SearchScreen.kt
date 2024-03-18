package com.jjddww.awesomecoffee.compose.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

@Composable
fun SearchScreen(onMenuSelected: (Int) -> Unit){
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        BasicTextField(modifier = Modifier
            .width(200.dp)
            .height(60.dp),
            value = "",
            onValueChange = {}){

        }
    }
}