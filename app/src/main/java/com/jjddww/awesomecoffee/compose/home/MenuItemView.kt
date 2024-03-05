package com.jjddww.awesomecoffee.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun MenuItemView(imageUrl: String, menuName: String, onClick:() -> Unit){

    Column(modifier = Modifier
        .width(140.dp)
        .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally){

        Image(
            painter = rememberImagePainter (data = imageUrl),
            contentDescription = "AdsImage",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(14.dp))

        Text(text = menuName,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black)

    }
}