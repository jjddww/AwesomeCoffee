package com.jjddww.awesomecoffee.compose.payment

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.utilities.ApplyDecimalFormat
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import com.jjddww.awesomecoffee.viewmodels.CartViewModel
import java.lang.String.format

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(viewModel: CartViewModel, navController: NavController,
               onPaymentScreen:() -> Unit, onNavigateRoute: (String) -> Unit){

    val context = LocalContext.current
    val cartList by viewModel.cartItems.observeAsState(initial = emptyList())
    val checkAllBoxState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val totalPrice = viewModel.totalPrice
    val deleteAllItems = {viewModel.deleteAllItems()}
    val deleteCheckedItems = { menuName: String, option: String, shot: Boolean ->
        viewModel.deleteCheckedItems(menuName, option, shot)}

    val onIncreaseAmount = { item: Cart -> viewModel.addCartItem(item)}

    val onChangeItemChecked = {
            check: Boolean, menuName: String, option: String, extraShot: Boolean ->
        viewModel.updateChecked(check, menuName, option, extraShot)
    }

    val onChangeTotalPrice = { item: Cart, checked: Boolean ->
        viewModel.operateTotalPrice(item, checked)
    }

    val decreaseTotalPrice = { item: Cart ->
        viewModel.decreaseTotalPrice(item)
    }

    val increaseTotalPrice = { item: Cart ->
        viewModel.increaseTotalPrice(item)
    }

    val initTotalPrice = { viewModel.initialTotalPrice() }


    val detectAllChecked = {
        var allchecked = true
        cartList.forEach {
            if(!it.checked)
            {
                allchecked = false
                return@forEach
            }
        }
        if(allchecked)
            checkAllBoxState.value = true

    }

    LaunchedEffect(cartList.isNotEmpty()){
        viewModel.initCheckBoxState()
        viewModel.initialTotalPrice()
    }


    Scaffold(bottomBar = { AppBottomBar(navController = navController, onNavigateRoute) },
        containerColor = surfaceVariantLight
    ){

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)) {

            Spacer(Modifier.height(35.dp))

            Text(text = stringResource(id = R.string.cart),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = tertiaryLight
            )


            if(cartList.isEmpty())
            {
                Column(Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text= stringResource(id = R.string.empty_cart),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 300.dp),
                        textAlign = TextAlign.Center)
                }
            }

            else{

                Spacer(Modifier.height(50.dp))

                CheckAllItemsView(initTotalPrice = initTotalPrice,
                    onChangeTotalPrice = onChangeTotalPrice,
                    checkAllBoxState = checkAllBoxState,
                    onChangeItemChecked = onChangeItemChecked, deleteAllItems = deleteAllItems,
                    deleteCheckedItems = deleteCheckedItems, cartList= cartList)

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 5.dp, end = 5.dp),
                    color = outlineDarkHighContrast
                )

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .height(500.dp)){
                    items(cartList){
                        CartItem(item = it,
                            decreaseTotalPrice = decreaseTotalPrice,
                            increaseTotalPrice = increaseTotalPrice,
                            detectAllChecked = detectAllChecked,
                            onChangeItemChecked = onChangeItemChecked,
                            checkAllBoxState = checkAllBoxState,
                            onIncreaseAmount = onIncreaseAmount,
                            onChangeTotalPrice = onChangeTotalPrice)
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = 110.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = format(
                            stringResource(id = R.string.total_price_format),
                            ApplyDecimalFormat(totalPrice.value)
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 20.dp, top = 10.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
                        color = Color.Black, fontSize = 20.sp
                    )

                    Button(modifier = Modifier
                        .width(150.dp)
                        .height(40.dp)
                        .padding(end = 20.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.textButtonColors(onSurfaceVariantLight),
                        onClick = {
                            if(totalPrice.value > 0){
                                onPaymentScreen()
                            }
                            else
                                Toast.makeText(context, R.string.notice_select_menu, Toast.LENGTH_SHORT).show()
                        })
                    {
                        Text(
                            stringResource(id = R.string.payment),
                            fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun CheckAllItemsView(
    initTotalPrice:() -> Unit,
    onChangeTotalPrice: (Cart, Boolean) -> Unit,
    checkAllBoxState: MutableState<Boolean>,
    onChangeItemChecked: (Boolean, String, String, Boolean) -> Unit,
    deleteAllItems: ()-> Unit,
    deleteCheckedItems: (String, String, Boolean) -> Unit,
    cartList: List<Cart>
){

    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp), horizontalArrangement = Arrangement.SpaceBetween){

        Row{
            Checkbox(checked = checkAllBoxState.value,
                onCheckedChange = {
                    checkAllBoxState.value = !checkAllBoxState.value

                    initTotalPrice()

                    cartList.forEach { item ->
                        onChangeItemChecked(
                            checkAllBoxState.value,
                            item.menuName,
                            item.option,
                            item.shot
                        )

                        if(checkAllBoxState.value)
                            onChangeTotalPrice(item, true)
                    }
                },
                modifier = Modifier.padding(start = 20.dp),
                colors = CheckboxDefaults.colors(checkedColor = surfaceVariant))

            Text(
                modifier = Modifier
                    .clickable {
                        checkAllBoxState.value = !checkAllBoxState.value

                        initTotalPrice()

                        cartList.forEach { item ->
                            onChangeItemChecked(
                                checkAllBoxState.value,
                                item.menuName,
                                item.option,
                                item.shot
                            )

                            if(checkAllBoxState.value)
                                onChangeTotalPrice(item, true)
                        }
                    }
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.all_selected),
                style = MaterialTheme.typography.labelMedium
            )
        }


        Button(onClick = { if(checkAllBoxState.value) deleteAllItems()
                           else{
                             cartList.forEach { item ->
                                 if(item.checked)
                                    deleteCheckedItems(item.menuName, item.option, item.shot)
                             }
                                initTotalPrice()
                         }
                            checkAllBoxState.value = false },
            modifier = Modifier
                .width(130.dp)
                .height(40.dp)
                .padding(end = 20.dp)
                .align(Alignment.CenterVertically),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(onSurfaceVariantLight)) {

            Text(text = stringResource(id = R.string.remove_selected_box), modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center)
        }
    }
}
