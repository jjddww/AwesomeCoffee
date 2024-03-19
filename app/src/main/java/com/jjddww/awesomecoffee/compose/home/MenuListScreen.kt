package com.jjddww.awesomecoffee.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import com.jjddww.awesomecoffee.data.model.Menu

@Composable
fun MenuListScreen(modifier: Modifier,
                   menuList: List<Menu> = arrayListOf(),
                   onMenuSelected: (Int) -> Unit) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        items(menuList) {menu ->
            HomeMenuItemView(menu, onMenuSelected)
        }
    }
}