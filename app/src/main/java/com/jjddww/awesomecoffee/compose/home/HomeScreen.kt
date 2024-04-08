package com.jjddww.awesomecoffee.compose.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.utilities.delayTime
import com.jjddww.awesomecoffee.utilities.stampCount
import com.jjddww.awesomecoffee.viewmodels.HomeViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateRoute: (String) -> Unit,
    onMenuSelected: (Int) -> Unit
){
    val viewModel = HomeViewModel()
    val imageUrlList by viewModel.advertisements.observeAsState(initial = emptyList())
    val recommendedMenuList by viewModel.recommendedMenuList.observeAsState(initial = emptyList())
    val newMenuList by viewModel.newMenuList.observeAsState(initial = emptyList())
    val stamp by viewModel.stampCount.observeAsState(initial = 0)
    val emptyImageUrl = stringResource(id = R.string.empty_ads_image_url)
    val pagerState = rememberPagerState (pageCount = {imageUrlList.size})
    val isDragged = pagerState.interactionSource.collectIsDraggedAsState()
    val scrollState = rememberScrollState()

    val settingMemberInfo = { viewModel.settingMemberInfo() }
    val getStampCount = { viewModel.getStampCount()}

    settingMemberInfo()
    getStampCount()

    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute) },
        containerColor = surfaceVariantLight)
    {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(35.dp))

            Text(text = stringResource(id = R.string.login_app_name),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = tertiaryLight)

            Spacer(modifier = Modifier.height(35.dp))

            AdsImageHorizontalPager(
                imageUrlList,
                emptyImageUrl,
                Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .align(Alignment.CenterHorizontally),
                pagerState)

            Spacer(modifier = Modifier.height(25.dp))

            CouponStampView(stampCount, stamp)

            Spacer(modifier = Modifier.height(39.dp))

            MenuListView(title = stringResource(id = R.string.new_menu), menuList = newMenuList, onMenuSelected = onMenuSelected)

            Spacer(modifier = Modifier.height(35.dp))

            MenuListView(title = stringResource(id = R.string.recommended_menu), menuList = recommendedMenuList, onMenuSelected = onMenuSelected)

            Spacer(modifier = Modifier.height(80.dp))
        }

        LaunchedEffect(key1 = pagerState.currentPage){
            snapshotFlow { isDragged.value }
                .collectLatest {checkDragged ->
                    if(checkDragged) return@collectLatest

                    launch {
                        withContext(NonCancellable){
                            delay(delayTime)
                            val target = if(pagerState.currentPage < imageUrlList.count() - 1) pagerState.currentPage.inc() else 0

                            pagerState.animateScrollToPage(page = target)
                        }
                    }
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
                contentScale = ContentScale.FillWidth,
                contentDescription = "AdsImage")
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
                    if(pagerState.currentPage == it) tertiaryLight else Color.White
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
                .height(120.dp)
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

@Composable
fun MenuListView(title: String, menuList: List<Menu>, onMenuSelected: (Int) -> Unit){
    Text(text = title,
        modifier = Modifier.padding(start = 20.dp),
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black)

    Spacer(modifier = Modifier.height(22.dp))

    MenuListScreen(modifier = Modifier
        .height(150.dp),
        menuList = menuList,
        onMenuSelected = onMenuSelected)
}