package com.jjddww.awesomecoffee.compose.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.ui.theme.neutralVariant70

@Composable
fun MenuItem(menu: Menu){
    Row(modifier = Modifier
        .width(140.dp)
        .height(130.dp)
        .padding(start = 15.dp)){

        Image(
            painter = rememberImagePainter (data = menu.imgUrl),
            contentDescription = "menuImage",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
        )

        Spacer(Modifier.width(30.dp))

        Column (modifier = Modifier.height(130.dp)){
            Text(text = menu.menuName,
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 14.sp,
                color = Color.Black)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = menu.englishMenuName,
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                fontSize = 12.sp,
                color = neutralVariant70)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "${menu.price}원",
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 14.sp,
                color = Color.Black)
        }
    }
}

@Composable
fun MenuVerticalList(menuList: List<Menu>){
    LazyColumn(modifier = Modifier.height(200.dp)){

        items(menuList){menu ->
            MenuItem(menu = menu)
        }
    }
}