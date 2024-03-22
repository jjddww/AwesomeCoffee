package com.jjddww.awesomecoffee.compose.payment

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.ui.theme.neutralVariant70
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.utilities.ApplyDecimalFormat
import java.lang.String


@Composable
fun CartItem(item: Cart,
             detectAllChecked:() -> Unit,
             checkAllBoxState: MutableState<Boolean>,
             onIncreaseAmount: (Cart) -> Unit){
    val checkBoxState = remember { mutableStateOf(false) }

    checkBoxState.value = item.checked.value

    Column(
        Modifier
            .fillMaxWidth()
            .height(215.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().
            padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Checkbox(checked = checkBoxState.value,
                onCheckedChange = {
                    item.checked.value = !item.checked.value
                    checkBoxState.value = item.checked.value
                    if(!item.checked.value)
                        checkAllBoxState.value = false

                    detectAllChecked()
                },
                colors = CheckboxDefaults.colors(checkedColor = surfaceVariant))

            Image(painter = rememberImagePainter(data = item.url),
                modifier = Modifier
                    .width(95.dp)
                    .height(95.dp),
                contentDescription = null,
                contentScale = ContentScale.FillBounds)

            Column (
                Modifier
                    .wrapContentSize()
                    .padding(start = 10.dp, end = 10.dp)){
                Text(text = item.menuName, style = MaterialTheme.typography.labelSmall)
                Text(text = item.option, modifier = Modifier.padding(top = 5.dp),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                    color = neutralVariant70, fontSize = 12.sp)

                if(item.shot)
                    Text(text = stringResource(id = R.string.extra_shot), modifier = Modifier.padding(top = 5.dp),
                        fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                        color = neutralVariant70, fontSize = 12.sp)
            }

            Text(text = String.format(stringResource(id = R.string.price_format), ApplyDecimalFormat (item.price)),
                modifier = Modifier.padding(end = 10.dp),
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                color = neutralVariant70, fontSize = 12.sp
            )
        }


        SetAmountButtonView(onIncreaseAmount, item)

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 5.dp, end = 5.dp, top= 20.dp),
            color = outlineDarkHighContrast
        )
    }
}


@Composable
fun SetAmountButtonView(onIncreaseAmount: (Cart) -> Unit,
                        item: Cart){

    var cnt = mutableStateOf(item.amount)

    Row(modifier = Modifier
        .wrapContentWidth()
        .padding(start = 20.dp, top = 30.dp)){

        IconButton(onClick = {
            if(cnt.value > 1){
                cnt.value -= 1
                item.amount = cnt.value
                onIncreaseAmount(item)
            }
        },
            enabled = cnt.value > 1,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)){
            Icon(painter = painterResource(id = R.drawable.baseline_remove_circle_outline_24),
                contentDescription = null)
        }

        Text(text = "${cnt.value}", modifier = Modifier.padding(start = 8.dp, end = 8.dp), style = MaterialTheme.typography.titleMedium)

        IconButton(onClick = {
            cnt.value += 1
            item.amount = cnt.value
            onIncreaseAmount(item)
        },
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)){
            Icon(painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                contentDescription = null)
        }

        Text(text = String.format(
            stringResource(id = R.string.price_format),
            ApplyDecimalFormat(if(item.shot) {item.amount * item.price + 500 * item.amount}
            else {item.amount * item.price})
        ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium)

    }
}