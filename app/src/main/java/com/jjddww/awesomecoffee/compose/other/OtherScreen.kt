package com.jjddww.awesomecoffee.compose.other

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.ui.theme.lightGray
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight

enum class OtherItems (
    @DrawableRes val img: Int,
    val text: String
){
    NOTICE(R.drawable.baseline_event_note_24, "공지사항 및 이벤트"),
    HISTORY(R.drawable.baseline_receipt_long_24, "주문내역")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtherScreen(navController: NavController, onNavigateRoute: (String) -> Unit){

    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute) },
        containerColor = surfaceVariantLight){

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier.padding(10.dp)) {
                items(OtherItems.entries.size){
                    OtherItemBoxLayout(img = OtherItems.entries[it].img,
                                       text = OtherItems.entries[it].text)
                }
            }


        }
    }
}

@Composable
fun OtherItemBoxLayout(
    @DrawableRes img: Int,
    text: String
){

    Box(modifier = Modifier
        .width(120.dp)
        .height(90.dp)
        .border(width = 1.dp, color = lightGray, shape = RoundedCornerShape(10.dp)))
    {

        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = img), contentDescription = "")
            Spacer(modifier =Modifier.height(7.dp))
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        }
    }
}
