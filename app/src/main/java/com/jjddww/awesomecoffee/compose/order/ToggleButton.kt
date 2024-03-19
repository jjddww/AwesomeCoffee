package com.jjddww.awesomecoffee.compose.order

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.content.contentValuesOf
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.inversePrimaryDark
import com.jjddww.awesomecoffee.ui.theme.primaryContainerDarkMediumContrast
import com.jjddww.awesomecoffee.ui.theme.primaryLight
import com.jjddww.awesomecoffee.ui.theme.secondaryContainerLight
import com.jjddww.awesomecoffee.ui.theme.secondaryDarkMediumContrast
import com.jjddww.awesomecoffee.utilities.HOT
import com.jjddww.awesomecoffee.utilities.ICED
import okhttp3.internal.format

const val FIRST_INDEX = 0
const val SECOND_INDEX = 1

enum class Sizes(
    @StringRes val text: Int,
    @DrawableRes val drawable: Int,
    var milliliter: Int
) {
    REGULAR(R.string.regular, R.drawable.outline_coffee_24, 355),
    LARGE(R.string.Large, R.drawable.outline_coffee_30, 470),
    EXTRA(R.string.Extra, R.drawable.outline_coffee_35, 590)
}

enum class Cups(
    @StringRes val text: Int
){
    PERSONAL(R.string.personal_cup),
    RESTAURANT_USE_CUP(R.string.restaurant_use_cup),
    SINGE_USE_CUP(R.string.single_use_cup)
}

enum class Temperature(
    @StringRes val text: Int
){
    HOT(R.string.hot),
    ICED(R.string.iced)
}

enum class Takeout(
    @StringRes val text: Int
){
    TOGO(R.string.to_go),
    TAKEOUT(R.string.take_out)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeToggleButton(){
    val items = Sizes.entries.toTypedArray()

    var selectedOption by remember {
        mutableStateOf(0)
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){

        SingleChoiceSegmentedButtonRow (modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(start = 15.dp, end = 15.dp, top = 12.dp)){
            items.forEachIndexed{ index, item ->

                SegmentedButton(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                    selected = index == selectedOption,
                    onClick = { selectedOption = index },
                    colors = SegmentedButtonDefaults.colors(activeContainerColor = secondaryContainerLight,
                        activeBorderColor = primaryContainerDarkMediumContrast, activeContentColor = primaryLight,
                        inactiveContainerColor = Color.White, inactiveBorderColor = Color.Black),
                    shape = RoundedCornerShape(percent = 15),
                    icon = {}
                ) {
                    Column (
                        Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .padding(bottom = 3.dp), verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Icon(painter = painterResource(id = item.drawable), contentDescription = null,)

                        Text(text = stringResource(id = item.text),
                            style = MaterialTheme.typography.bodySmall)

                        Text(text = format(stringResource(id = R.string.milliliter), item.milliliter),
                            fontFamily = FontFamily(Font(R.font.spoqahansansneo_light)),
                            fontSize = 12.sp)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupToggleButton(){
    val items = Cups.entries.toTypedArray()

    var selectedOption by remember {
        mutableStateOf(0)
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){

        SingleChoiceSegmentedButtonRow (modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(start = 15.dp, end = 15.dp, top = 20.dp)){
            items.forEachIndexed{ index, item ->

                SegmentedButton(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                    selected = index == selectedOption,
                    onClick = { selectedOption = index },
                    colors = SegmentedButtonDefaults.colors(activeContainerColor = secondaryContainerLight,
                        activeBorderColor = primaryContainerDarkMediumContrast, activeContentColor = primaryLight,
                        inactiveContainerColor = Color.White, inactiveBorderColor = Color.Black),
                    shape = RoundedCornerShape(percent = 15),
                    icon = {}
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = stringResource(id = item.text),
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureToggleButton(){
    val items = Temperature.entries.toTypedArray()
    var selectedOption by remember {
        mutableStateOf(0)
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){

        SingleChoiceSegmentedButtonRow (modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)){
            items.forEachIndexed{ index, item ->

                SegmentedButton(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                    selected = index == selectedOption,
                    onClick = { selectedOption = index },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = if (index == 0) Color.Red else Color.Blue,
                        activeBorderColor = if (index == 0) Color.Red else Color.Blue,
                        activeContentColor = Color.White,
                        inactiveContainerColor = Color.White,
                        inactiveBorderColor = Color.Black,
                        inactiveContentColor = Color.Black),
                    shape = RoundedCornerShape(percent = 15),
                    icon = {}
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = stringResource(id = item.text),
                            style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedTempToggleButton(temperature: String){
    val item = if(temperature == HOT) Temperature.entries.toTypedArray()[FIRST_INDEX]
        else Temperature.entries.toTypedArray()[SECOND_INDEX]

    var selectedOption by remember {
        mutableStateOf(0)
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){

        SingleChoiceSegmentedButtonRow (modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)){
                SegmentedButton(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp),
                    onClick = { selectedOption = 0 },
                    selected = true,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = if(temperature == HOT) Color.Red else Color.Blue,
                        activeBorderColor = if(temperature == HOT) Color.Red else Color.Blue,
                        activeContentColor = Color.White),
                    shape = RoundedCornerShape(percent = 15),
                    icon = {}
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = format(stringResource(id = R.string.only), stringResource(id = item.text)),
                            style = MaterialTheme.typography.titleSmall)
                    }
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeoutToggleButton(){
    val items = Takeout.entries.toTypedArray()

    var selectedOption by remember {
        mutableStateOf(0)
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){

        SingleChoiceSegmentedButtonRow (modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 40.dp)){
            items.forEachIndexed{ index, item ->

                SegmentedButton(
                    modifier = Modifier
                        .padding(start = 3.dp, end = 3.dp)
                        .height(80.dp),
                    selected = index == selectedOption,
                    onClick = { selectedOption = index },
                    colors = SegmentedButtonDefaults.colors(activeContainerColor = secondaryContainerLight,
                        activeBorderColor = primaryContainerDarkMediumContrast, activeContentColor = primaryLight,
                        inactiveContainerColor = Color.White, inactiveBorderColor = Color.Black),
                    shape = RoundedCornerShape(percent = 15),
                    icon = {}
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = stringResource(id = item.text),
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonPreview(){
    SizeToggleButton()
}

@Preview(showBackground = true)
@Composable
fun CupButtonPreview(){
    CupToggleButton()
}

@Preview(showBackground = true)
@Composable
fun TemperaturePreview(){
    TemperatureToggleButton()
}

@Preview(showBackground = true)
@Composable
fun FixedButtonPreview(){
    Column{
        FixedTempToggleButton(HOT)
        FixedTempToggleButton(ICED)
    }
}

@Preview(showBackground = true)
@Composable
fun TakeoutButtonPreview(){
    TakeoutToggleButton()
}



