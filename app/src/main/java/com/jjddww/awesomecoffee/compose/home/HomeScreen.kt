package com.jjddww.awesomecoffee.compose.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.backgroundLight
import com.jjddww.awesomecoffee.ui.theme.scrimLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight


@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(35.dp))

        Text(text = stringResource(id = R.string.login_app_name),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight)

        Spacer(modifier = Modifier.height(39.dp))

        //TODO 배너 넣기
        AdsImageHorizontalPager(5,
            Modifier
                .fillMaxWidth()
                .height(180.dp))

        Spacer(modifier = Modifier.height(25.dp))

        MoveToLogin()

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdsImageHorizontalPager(
    count: Int,
    modifier: Modifier,
){
    val pagerState = rememberPagerState (pageCount = {count})

    HorizontalPager(state = pagerState, modifier = modifier) {
        Text(
            text = it.toString(),
            modifier = Modifier.fillMaxSize(),
            fontSize= 30.sp)
    }
}


@Composable
fun MoveToLogin(){
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = backgroundLight, shape = RectangleShape)
    ){
        Text(
            text = stringResource(id = R.string.move_to_login),
            modifier = Modifier.padding(start = 15.dp, top = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = scrimLight)

        Text(
            text = stringResource(id = R.string.move_to_login2),
            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
            style = MaterialTheme.typography.titleSmall,
            color = scrimLight)
    }
}

@Composable
@Preview(showSystemUi = true)
fun Preview(){
    AwesomeCoffeeTheme {
        Surface (color = surfaceVariantLight){
            HomeScreen()
        }
    }
}