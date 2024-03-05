package com.jjddww.awesomecoffee.compose.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jjddww.awesomecoffee.data.model.Menu

@Composable
fun MenuListScreen(modifier: Modifier,
                   menuList: List<Menu> = arrayListOf()) {
    val context = LocalContext.current

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        items(menuList) {
            MenuItemView(it.imgUrl, it.menuName) {
            }
        }
    }
}