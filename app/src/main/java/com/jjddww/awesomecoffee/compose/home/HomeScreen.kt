package com.jjddww.awesomecoffee.compose.home

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.backgroundLight
import com.jjddww.awesomecoffee.ui.theme.scrimLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryContainerLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.AdImageUrlListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val delayTime: Long = 3500

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: AdImageUrlListViewModel
){
    val imageUrlList by viewModel.advertisementUrl.observeAsState(initial = emptyList())
    val emptyImageUrl = stringResource(id = R.string.empty_ads_image_url)
    val pagerState = rememberPagerState (pageCount = {imageUrlList.size})

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

        MoveToLogin()

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
    imageUrlList: List<String>,
    emptyImageUrl: String,
    modifier: Modifier,
    pagerState: PagerState
){

    Box{
        HorizontalPager(state = pagerState, modifier = modifier) {

            Image(painter = rememberImagePainter
                (data = if (imageUrlList.isNotEmpty()) imageUrlList[it]
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

//@Composable
//@Preview(showSystemUi = true)
//fun Preview() {
//    AwesomeCoffeeTheme {
//        Surface(color = surfaceVariantLight) {
//            val owner = LocalViewModelStoreOwner.current
//
//            owner?.let {
//                val viewModel: AdImageUrlListViewModel = viewModel(
//                    it,
//                    "AdImageUrlListViewModel",
//                    HomeViewModelFactory(
//                        LocalContext.current.applicationContext as Application
//                    )
//                )
//                HomeScreen(viewModel)
//            }
//
//        }
//    }
//}
