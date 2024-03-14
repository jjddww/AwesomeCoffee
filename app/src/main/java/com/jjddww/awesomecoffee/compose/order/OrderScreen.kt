package com.jjddww.awesomecoffee.compose.order

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.model.SubCategory
import com.jjddww.awesomecoffee.ui.theme.black60
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.outlineVariantLight
import com.jjddww.awesomecoffee.ui.theme.surfaceContainerHighLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.utilities.BEVERAGE
import com.jjddww.awesomecoffee.utilities.DESSERT
import com.jjddww.awesomecoffee.viewmodels.OrderViewModel
import kotlinx.coroutines.launch

enum class CategoryPages(
    @StringRes val titleId: Int,
){
    BEVERAGE(R.string.beverage),
    DESSERT(R.string.dessert)
}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(navController: NavController, onNavigateRoute: (String) -> Unit, onMenuSelected: (Int) -> Unit){

    Log.e("띠용", "dddddd")

    val viewModel = OrderViewModel()
    val pagerState = rememberPagerState (pageCount = {CategoryPages.entries.size})
    val beverageCategories by viewModel.beverageCategories.observeAsState(initial = emptyList())
    val dessertCategories by viewModel.dessertCategories.observeAsState(initial = emptyList())
    val menuList by viewModel.menuData.observeAsState(initial = emptyList())
    val partitionBeverageMenuList by viewModel.beverageMenuList.observeAsState(initial = emptyList())
    val partitionDessertMenuList by viewModel.dessertMenuList.observeAsState(initial = emptyList())
    val beverageIndex by viewModel.beverageListIndex.observeAsState(initial = 0)
    val dessertIndex by viewModel.dessertListIndex.observeAsState(initial = 0)

    val onBeverageChangeList = { c: String -> viewModel.setBeverageMenuList(c)}
    val onDessertChangeList = { c: String -> viewModel.setDessertMenuList(c)}
    val onChangeIndex = { c: String, idx: Int -> viewModel.changeIndex(c, idx)}


    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute)},
        containerColor = surfaceVariantLight){

        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Spacer(Modifier.height(35.dp))

            Text(text = stringResource(id = R.string.order),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = tertiaryLight
            )


            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 15.dp)
            ) {
                Icon(painterResource(id = R.drawable.baseline_search_30), contentDescription = null)
            }

            if (!beverageCategories.isNullOrEmpty())
                onBeverageChangeList(beverageCategories[beverageIndex].subCategory)

            SearchPagerScreen(onChangeIndex, beverageIndex, dessertIndex, onBeverageChangeList,
                onDessertChangeList, partitionBeverageMenuList, partitionDessertMenuList, beverageCategories,
                dessertCategories, pagerState, onMenuSelected = onMenuSelected)


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchPagerScreen(
    onChangeIndex: (String, Int) -> Unit,
    beverageIndex: Int,
    dessertIndex: Int,
    onChangeBeverageList: (String) -> Unit,
    onChangeDessertList: (String) -> Unit,
    p1: List<Menu>,
    p2: List<Menu>,
    beverageList: List<SubCategory>,
    dessertList: List<SubCategory>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onMenuSelected: (Int) -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val pages = CategoryPages.entries.toTypedArray()

    Column(modifier) {
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
                .fillMaxSize()) {
            index ->
            when(pages[index]){
                CategoryPages.BEVERAGE -> {
                    if(beverageList.isNotEmpty())
                        onChangeBeverageList(beverageList[beverageIndex].subCategory)
                    OrderPageScreen(onChangeIndex, BEVERAGE, p1,  beverageList, beverageIndex, onMenuSelected)
                }

                CategoryPages.DESSERT -> {
                    if(dessertList.isNotEmpty())
                        onChangeDessertList(dessertList[dessertIndex].subCategory)
                    OrderPageScreen(onChangeIndex, DESSERT, p2, dessertList, dessertIndex, onMenuSelected)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPageScreen(
    onChangeIndex: (String, Int) -> Unit,
    mainCategory: String,
    p: List<Menu>,
    categories: List<SubCategory>,
    savedIndex: Int,
    onMenuSelected: (Int) -> Unit){
    var selectedOption by remember { mutableStateOf(savedIndex) }
    var selectedCategories by remember { mutableStateOf(if (categories.isNotEmpty()) categories[savedIndex].subCategory else "") }


    Column(Modifier.fillMaxSize()){
        SingleChoiceSegmentedButtonRow(modifier= Modifier
            .height(70.dp)){
            categories.forEachIndexed{ index, item ->

                SegmentedButton(selected = index == selectedOption,
                    modifier = Modifier
                        .width(110.dp)
                        .padding(start = if (index == 0) 20.dp else 15.dp) ,
                    onClick = { selectedOption = index
                        onChangeIndex( mainCategory, selectedOption)
                        selectedCategories = item.subCategory },
                    icon = {},
                    colors = SegmentedButtonDefaults.colors(activeContainerColor = onSurfaceVariantLight,
                        activeBorderColor = onSurfaceVariantLight, activeContentColor = Color.White,
                        inactiveContainerColor = surfaceContainerHighLight, inactiveContentColor = onSurfaceVariantLight,
                        inactiveBorderColor = surfaceContainerHighLight),
                    shape = RoundedCornerShape(20.dp)) {

                    Text(text = item.subCategory,
                        fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                }

            }
        }

        MenuVerticalList(menuList = p, onMenuSelected)

    }


}