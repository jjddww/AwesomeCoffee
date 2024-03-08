package com.jjddww.awesomecoffee.compose.order

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.ui.theme.black60
import com.jjddww.awesomecoffee.ui.theme.neutralVariant70
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.DetailViewModel
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Shape
import com.jjddww.awesomecoffee.ui.theme.onSecondaryLight
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.secondaryLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import kotlinx.coroutines.launch

enum class Pages(
    @StringRes val titleId: Int
){
    MENU_DESC(R.string.desc_title),
    NUTRITION_INFORMATION(R.string.nutrition_info)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    viewModel: DetailViewModel){

    val menuDescriptionResult by viewModel.description.observeAsState(initial = emptyList())
    val desc = if (menuDescriptionResult.isNotEmpty()) menuDescriptionResult[0] else Menu()
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(pageCount = {Pages.values().size})
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxWidth()){

        Image(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
            painter = rememberImagePainter(data = desc.imgUrl),
            contentDescription = "Menu Image",
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.height(33.dp))

        Text(text = desc.menuName,
            modifier= Modifier.padding(start = 20.dp),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight)

        Text(text = desc.englishMenuName,
            modifier = Modifier.padding(start = 20.dp, top = 2.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
            color = neutralVariant70,
            fontSize = 18.sp)

        Text(text = "${desc.price}원",
            modifier = Modifier.padding(start = 20.dp, top = 19.dp),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)

        Spacer(modifier = Modifier.height(30.dp))

        DescPagerScreen(pagerState = pagerState,
            modifier = Modifier.fillMaxWidth(),
            menuDescription = desc)

        Button(modifier = Modifier
            .width(270.dp)
            .height(40.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.textButtonColors(onSurfaceVariantLight),
            onClick = {
                showBottomSheet = true
            })
        {
            Text(stringResource(id = R.string.order),
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                color = Color.White,
                fontSize = 14.sp)
        }

        if(showBottomSheet){
            ModalBottomSheet(onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = onSecondaryLight
            )
            {
                BottomSheetContent()
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DescPagerScreen(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pages: Array<Pages> = Pages.values(),
    menuDescription: Menu
){
    Column(modifier) {

        val coroutineScope = rememberCoroutineScope() //코루틴 스코프 쓰는 이유?

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = surfaceVariantLight,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = tertiaryLight
                )
            }){
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleId)

                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {coroutineScope.launch { pagerState.animateScrollToPage(index) }},
                    text = { Text (text = title,
                                   modifier = Modifier.align(Alignment.CenterHorizontally),
                                   style = MaterialTheme.typography.labelMedium )},
                    unselectedContentColor = black60,
                    selectedContentColor = tertiaryLight
                )
            }
        }


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            ) {
            index ->
            when(pages[index]){
                Pages.MENU_DESC -> {
                    PageScreen(menuDescription.desc)
                }
                
                Pages.NUTRITION_INFORMATION -> {
                    PageScreen("영양정보 없음.")
                }
            }
        }

    }
}

@Composable
fun PageScreen(menuDescription: String){
    Column (
        Modifier
            .fillMaxSize()
            .padding(top = 30.dp)){
        Text(text = menuDescription,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black)
    }
}

@Composable
fun BottomSheetContent(){
    Column(Modifier.fillMaxSize()
        .background(onSecondaryLight)){
        Text(text = "옵션선택",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp))

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "사이즈", modifier = Modifier.padding(start = 43.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp)
    }
}


