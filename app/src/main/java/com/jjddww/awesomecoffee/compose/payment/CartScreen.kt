package com.jjddww.awesomecoffee.compose.payment

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.CartViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(viewModel: CartViewModel, navController: NavController, onNavigateRoute: (String) -> Unit){

    val cartList by viewModel.cartItems.observeAsState(initial = emptyList())
    val checkAllBoxState = remember { mutableStateOf(false) }

    val deleteAllItems = {viewModel.deleteAllItems()}

    Scaffold(bottomBar = { AppBottomBar(navController = navController, onNavigateRoute) },
        containerColor = surfaceVariantLight
    ){

        Column(Modifier.fillMaxSize()) {

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
                        style = MaterialTheme.typography.labelMedium)
                }
            }

            else{

                Spacer(Modifier.height(50.dp))

                CheckAllItemsView(checkAllBoxState = checkAllBoxState,
                    deleteAllItems = deleteAllItems)
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
                        .padding(bottom = 100.dp)){
                    items(cartList){
                        CartItem(item = it, checkAllBoxState = checkAllBoxState, onIncreaseAmount = {})
                    }
                }
            }
        }

    }
}

@Composable
fun CheckAllItemsView(
    checkAllBoxState: MutableState<Boolean>,
    deleteAllItems: ()-> Unit
){
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp), horizontalArrangement = Arrangement.SpaceBetween){

        Row{
            Checkbox(checked = checkAllBoxState.value,
                onCheckedChange = {
                    checkAllBoxState.value = it
                },
                modifier = Modifier.padding(start = 20.dp),
                colors = CheckboxDefaults.colors(checkedColor = surfaceVariant))

            Text(
                modifier = Modifier
                    .clickable {
                        checkAllBoxState.value = !checkAllBoxState.value
                    }
                    .align(Alignment.CenterVertically),
                text = stringResource(id = R.string.all_selected),
                style = MaterialTheme.typography.labelMedium
            )
        }


        Button(onClick = { if(checkAllBoxState.value) deleteAllItems()
                         else{

                         }},
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

@Preview(showBackground = true)
@Composable
fun Preview(){
    val checkAllBoxState = remember { mutableStateOf(false) }
    CheckAllItemsView(checkAllBoxState = checkAllBoxState, {})
    HorizontalDivider()
}