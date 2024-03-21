package com.jjddww.awesomecoffee.compose.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.ui.theme.onSecondaryLight
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.utilities.ApplyDecimalFormat
import com.jjddww.awesomecoffee.utilities.BEVERAGE
import com.jjddww.awesomecoffee.utilities.DESSERT
import com.jjddww.awesomecoffee.utilities.HOT
import com.jjddww.awesomecoffee.utilities.ICED
import java.lang.String.format
import java.text.DecimalFormat


@Composable
fun BeverageContent(isCoffee: Boolean,
                    isOnlyIced: String,
                    price: Int,
                    menu: Menu,
                    onShotChange:(isExtra: Boolean) -> Unit,
                    onAmountChange:(Boolean) -> Unit,
                    onSettingOptions:(Any) -> Unit,
                    onAddCartItem:(Menu, String) -> Unit,
                    onMoveToPayment: () -> Unit,
                    amount: Int) {
    lateinit var checkBoxState: MutableState<Boolean>

    if (isCoffee)
        checkBoxState = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(onSecondaryLight)
    ) {
        Text(
            text = stringResource(id = R.string.option_select),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(id = R.string.hot_or_iced), modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )
        if(isOnlyIced != ICED && isOnlyIced != HOT){
            TemperatureToggleButton(onSettingOptions)
        }
        else{
            FixedTempToggleButton(temperature = isOnlyIced, onSettingOptions)
        }

        Text(
            text = stringResource(id = R.string.cup_size), modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )

        SizeToggleButton(onSettingOptions)

        CupToggleButton(onSettingOptions)

        if (isCoffee) {
            Text(
                text = stringResource(id = R.string.personal_option), modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
                fontSize = 15.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Checkbox(
                    checked = checkBoxState.value,
                    onCheckedChange = {
                        checkBoxState.value = it
                        if(checkBoxState.value)
                            onShotChange(true)
                        else
                            onShotChange(false)
                        },

                    modifier = Modifier.padding(start = 20.dp),
                    colors = CheckboxDefaults.colors(checkedColor = surfaceVariant)
                )

                Text(
                    modifier = Modifier.clickable {
                        checkBoxState.value = !checkBoxState.value
                        if(checkBoxState.value)
                            onShotChange(true)
                        else
                            onShotChange(false) },
                    text = stringResource(id = R.string.extra_shot),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        SetAmountButtonView(onAmountChange, amount, price)

        Spacer(Modifier.height(40.dp))

        OrderButtonView(BEVERAGE, menu, onAddCartItem, onMoveToPayment)
    }
}

@Composable
fun DessertContent(price: Int,
                   menu: Menu,
                   onAmountChange:(Boolean) -> Unit,
                   onSettingOptions:(String) -> Unit,
                   onAddCartItem:(Menu, String) -> Unit,
                   onMoveToPayment: () -> Unit,
                   amount: Int){

    Column (
        Modifier
            .fillMaxWidth()
            .background(onSecondaryLight)){

        Text(
            text = stringResource(id = R.string.option_select),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))


        Text(
            text = stringResource(id = R.string.select_take_out), modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )


        TakeoutToggleButton(onSettingOptions)

        SetAmountButtonView(onAmountChange, amount, price)

        Spacer(Modifier.height(40.dp))

        OrderButtonView(DESSERT, menu, onAddCartItem, onMoveToPayment)
    }
}


@Composable
fun SetAmountButtonView(onIncreaseAmount: (Boolean) -> Unit,
                        amount: Int,
                        price: Int){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp)){

        IconButton(onClick = {
            onIncreaseAmount(false)
        },
            enabled = amount > 1,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)){
            Icon(painter = painterResource(id = R.drawable.baseline_remove_circle_outline_24),
                contentDescription = null)
        }

        Text(text = "$amount", modifier = Modifier.padding(start = 8.dp, end = 8.dp), style = MaterialTheme.typography.titleMedium)

        IconButton(onClick = {
            onIncreaseAmount(true)
        },
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)){
            Icon(painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                contentDescription = null)
        }

        Text(text = format(stringResource(id = R.string.price_format), ApplyDecimalFormat(amount * price)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium)

    }
}

@Composable
fun OrderButtonView(mainCategory: String,
                    menu: Menu,
                    onAddCartItem:(Menu, String) -> Unit,
                    onMoveToPayment:() -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(start = 20.dp, end = 20.dp),
        Arrangement.SpaceEvenly){

        OrderButton(onClick = { onAddCartItem(menu, mainCategory) }, color = surfaceVariant, text = stringResource(
            id = R.string.add_cart
        )
        )

        OrderButton(onClick = { onMoveToPayment() }, color = onSurfaceVariantLight, text = stringResource(
            id = R.string.order
        )
        )
    }
}

@Composable
fun OrderButton(onClick: () -> Unit, color: Color, text: String) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .width(170.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = text)
    }
}
