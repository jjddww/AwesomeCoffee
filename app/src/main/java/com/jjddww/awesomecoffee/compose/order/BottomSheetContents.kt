package com.jjddww.awesomecoffee.compose.order

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.onSecondaryLight
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.utilities.HOT
import com.jjddww.awesomecoffee.utilities.ICED
import com.jjddww.awesomecoffee.viewmodels.DetailViewModel

@Composable
fun BeverageContent(isCoffee: Boolean,
                    isOnlyIced: String,
                    price: Int,
                    viewModel: DetailViewModel,
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
            text = "옵션선택",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "HOT / ICED", modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )
        if(isOnlyIced != ICED && isOnlyIced != HOT){
            TemperatureToggleButton()
        }
        else{
            FixedTempToggleButton(temperature = isOnlyIced)
        }

        Text(
            text = "사이즈", modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )

        SizeToggleButton()

        CupToggleButton()

        if (isCoffee) {
            Text(
                text = "퍼스널 옵션", modifier = Modifier.padding(start = 20.dp, top = 20.dp),
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
                            viewModel.extraShot()
                        else
                            viewModel.leaveOutShot()
                        },

                    modifier = Modifier.padding(start = 20.dp),
                    colors = CheckboxDefaults.colors(checkedColor = surfaceVariant)
                )

                Text(
                    modifier = Modifier.clickable {
                        checkBoxState.value = !checkBoxState.value
                        if(checkBoxState.value)
                            viewModel.extraShot()
                        else
                            viewModel.leaveOutShot() },
                    text = "샷 추가 (+500원)",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        SetAmountButtonView(viewModel, amount, price)

        Spacer(Modifier.height(40.dp))

        OrderButtonView(viewModel)
    }
}

@Composable
fun DessertContent(price: Int,
                   viewModel: DetailViewModel,
                   amount: Int){

    Column (
        Modifier
            .fillMaxWidth()
            .background(onSecondaryLight)){

        Text(
            text = "옵션선택",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))


        Text(
            text = "포장 여부", modifier = Modifier.padding(start = 20.dp),
            fontFamily = FontFamily(Font(R.font.spoqahansansneo_bold)),
            fontSize = 15.sp
        )


        TakeoutToggleButton()

        SetAmountButtonView(viewModel, amount, price)

        Spacer(Modifier.height(40.dp))

        OrderButtonView(viewModel)
    }
}


@Composable
fun SetAmountButtonView(viewModel: DetailViewModel, amount: Int, price: Int){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp)){

        IconButton(onClick = {
            viewModel.decreaseAmount()
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
            viewModel.increaseAmount()
        },
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)){
            Icon(painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                contentDescription = null)
        }

        Text(text = "${amount * price}원",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium)

    }
}

@Composable
fun OrderButtonView(viewModel: DetailViewModel){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(start = 20.dp, end = 20.dp),
        Arrangement.SpaceEvenly){

        OrderButton(onClick = { viewModel.changeBottomSheetState() }, color = surfaceVariant, text = stringResource(
            id = R.string.add_cart
        )
        )

        OrderButton(onClick = { viewModel.changeBottomSheetState() }, color = onSurfaceVariantLight, text = stringResource(
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
