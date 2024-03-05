package com.jjddww.awesomecoffee.compose.home

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.ui.theme.backgroundLight
import com.jjddww.awesomecoffee.ui.theme.scrimLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryContainerLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.utilities.delayTime
import com.jjddww.awesomecoffee.utilities.pointCount
import com.jjddww.awesomecoffee.utilities.stampCount
import com.jjddww.awesomecoffee.viewmodels.AdImageUrlListViewModel
import com.jjddww.awesomecoffee.viewmodels.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
){
    val imageUrlList by viewModel.advertisements.observeAsState(initial = emptyList())
    val emptyImageUrl = stringResource(id = R.string.empty_ads_image_url)
    val pagerState = rememberPagerState (pageCount = {imageUrlList.size})
    val isLogin = true

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(35.dp))

        Text(text = stringResource(id = R.string.login_app_name),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight)

        Spacer(modifier = Modifier.height(35.dp))

        AdsImageHorizontalPager(
            imageUrlList,
            emptyImageUrl,
            Modifier
                .fillMaxWidth()
                .height(280.dp),
            pagerState)

        Spacer(modifier = Modifier.height(25.dp))

        if (!isLogin)
            MoveToLogin()
        else
            CouponStampView(stampCount, pointCount)


        Spacer(modifier = Modifier.height(35.dp))


        Text(text = stringResource(id = R.string.recommended_menu),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)

        MenuListScreen(modifier =Modifier.fillMaxWidth().height(120.dp)
            , onClick = {})

        Text(text = stringResource(id = R.string.recommended_menu),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)

    }

    LaunchedEffect(key1 = pagerState.currentPage){
        launch {
            delay(delayTime)
            with(pagerState){
                val target = if(currentPage < imageUrlList.count() - 1) currentPage + 1 else 0

                animateScrollToPage(
                    page = target, animationSpec = tween(
                        durationMillis = 0, easing = FastOutLinearInEasing
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdsImageHorizontalPager(
    imageUrlList: List<BannerAd>,
    emptyImageUrl: String,
    modifier: Modifier,
    pagerState: PagerState
){

    Box{
        HorizontalPager(state = pagerState, modifier = modifier) {

            Image(painter = rememberImagePainter
                (data = if (imageUrlList.isNotEmpty()) imageUrlList[it].url
            else emptyImageUrl),
                contentDescription = "AdsImage",
                Modifier.fillMaxSize())
        }

        Row(
            Modifier
                .padding(start = 125.dp, bottom = 5.dp, end = 125.dp)
                .height(30.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            repeat(imageUrlList.size) {
                val color =
                    if(pagerState.currentPage == it) tertiaryContainerLight else Color.White
                Box(
                    modifier = Modifier
                        .padding(start = 17.dp)
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
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
fun CouponStampView(
    stampCount: Int,
    pointCount: Int){

    Column {
        Text(text = stringResource(id = R.string.stamp),
            modifier = Modifier.padding(start = 20.dp),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)
        
        Spacer(modifier = Modifier.height(10.dp))

        Row (Modifier
            .fillMaxWidth()){

            LazyVerticalGrid(modifier = Modifier
                .width(300.dp)
                .padding(start = 40.dp),
                columns = GridCells.Fixed(5),
                content= {

                    items(stampCount){count ->
                        Box(modifier = Modifier
                            .width(40.dp)
                            .height(60.dp),
                            contentAlignment = Alignment.Center){

                            Image(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(42.dp),
                                painter = painterResource(R.drawable.logo_login),
                                contentDescription = null)


                            if(count <= pointCount - 1){
                                Image(
                                    modifier = Modifier
                                        .width(38.dp)
                                        .height(50.dp)
                                        .padding(top = 10.dp),
                                    painter = painterResource(R.drawable.ic_stamp),
                                    contentDescription = null)
                            }

                        }
                    }
                })

            Spacer(Modifier.width(20.dp))

            Text(text = "$pointCount / 10",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleSmall,
                color = tertiaryLight)
        }
    }
}

fun Empty(){}