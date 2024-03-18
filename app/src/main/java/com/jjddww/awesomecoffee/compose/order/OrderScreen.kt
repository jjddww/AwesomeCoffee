package com.jjddww.awesomecoffee.compose.order

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.Menu
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


data class ChipState(
    var text: String,
    val isSelected: MutableState<Boolean>
)


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    viewModel: OrderViewModel, navController: NavController,
    onNavigateRoute: (String) -> Unit,
    onMenuSelected: (Int) -> Unit,
    onSearchScreen: () -> Unit
){

    val pagerState = rememberPagerState (pageCount = {CategoryPages.entries.size})
    val menuList by viewModel.menuData.observeAsState(initial = emptyList())
    val menuData1 by viewModel.filteredList1.observeAsState(initial = emptyList())
    val menuData2 by viewModel.filteredList2.observeAsState(initial = emptyList())

    val onChangeList = {m:String, c: String -> viewModel.setBeverageMenuList(m, c)}


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


            IconButton(onClick = { onSearchScreen() },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 15.dp)
            ) {
                Icon(painterResource(id = R.drawable.baseline_search_30), contentDescription = null)
            }

            SearchPagerScreen(menuData1, menuData2, pagerState, onChangeList = onChangeList, onMenuSelected = onMenuSelected)

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchPagerScreen(
    menuList: List<Menu>,
    menuList2: List<Menu>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onChangeList: (String , String) -> Unit,
    onMenuSelected: (Int) -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val categories = stringArrayResource(id = R.array.beverage)
    val categories2 = stringArrayResource(id = R.array.dessert)
    val states by rememberSaveable {
        mutableStateOf(
            mutableListOf(
                ChipState(categories[0], mutableStateOf(true)),
                ChipState(categories[1], mutableStateOf(false)),
                ChipState(categories[2], mutableStateOf(false))
            )
        )
    }

    val states2 by rememberSaveable {
        mutableStateOf(
            mutableListOf(
                ChipState(categories2[0], mutableStateOf(true)),
                ChipState(categories2[1], mutableStateOf(false))
            )
        )
    }

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

            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 20.dp)) {
                when(pages[index]){
                    CategoryPages.BEVERAGE -> {
                        Chips(
                            BEVERAGE,
                            categories,
                            onChangeList,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(start = 20.dp)
                            , elements = categories, states = states)

                        LazyColumn(Modifier.padding(bottom = 80.dp)){
                            items(menuList){menu ->
                                MenuItem(menu = menu, onMenuSelected = onMenuSelected)
                            }
                        }


                    }

                    CategoryPages.DESSERT -> {

                        Chips(
                            DESSERT,
                            categories2,
                            onChangeList,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(start = 20.dp),
                            elements = categories2, states = states2)

                        LazyColumn(Modifier.padding(bottom = 80.dp)){
                            items(menuList2){menu ->
                                MenuItem(menu = menu, onMenuSelected = onMenuSelected)
                            }
                        }

                    }
                }
            }
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp, bottom = 80.dp)){


            }
        }
    }
}



@Composable
fun Chips(
    main: String,
    category: Array<String>,
    onChangeList: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    elements: Array<String>,
    states: List<ChipState>,
){

    LazyRow(modifier = modifier) {
        items(elements.size) { index ->
            Chip(
                main,
                category,
                onChangeList,
                text = states[index].text,
                selected = states[index].isSelected.value,
                modifier = Modifier.height(60.dp),
                state = states,
                index = index
            )
        }
    }
}



@Composable
private fun Chip(
    main: String,
    category: Array<String>,
    onChangeList: (String, String) -> Unit,
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    state: List<ChipState>,
    index : Int
){

    onChangeList(main, category[state.indices.find { state[it].isSelected.value }!!])


    Surface(
        modifier = Modifier
            .padding(start = 5.dp, end = 10.dp)
            .wrapContentWidth(),
        color = when{
            selected -> onSurfaceVariantLight
            else -> outlineVariantLight
        },
        contentColor = when {
            selected -> Color.White
            else -> onSurfaceVariantLight
        },

        shape = RoundedCornerShape(20.dp),
    ){
        Row(
            Modifier
                .wrapContentWidth()
                .height(35.dp)
                .clickable {
                    state.forEachIndexed { idx, chipState ->
                        chipState.isSelected.value = index == idx
                    }
                    onChangeList(main, state[index].text)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = text, modifier = Modifier.padding(start =15.dp, end =15.dp),
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 14.sp)
        }
    }
}

