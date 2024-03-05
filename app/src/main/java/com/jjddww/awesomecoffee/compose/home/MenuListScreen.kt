package com.jjddww.awesomecoffee.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jjddww.awesomecoffee.data.model.Menu

@Composable
fun MenuListScreen(modifier: Modifier,
                   menus: List<Menu> = arrayListOf(),
                   onClick:() -> Unit){
    LazyRow(modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp))
    {
        items(menus){
            MenuItemView(it.imgUrl, it.menuName, onClick)
        }
    }
}