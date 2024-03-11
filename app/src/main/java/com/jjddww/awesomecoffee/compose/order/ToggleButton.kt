package com.jjddww.awesomecoffee.compose.order

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.solver.widgets.Rectangle
import com.jjddww.awesomecoffee.R

enum class Sizes(
    @StringRes val text: Int,
    @DrawableRes val drawable: Int
) {
    REGULAR(R.string.regular, R.drawable.outline_coffee_24),
    LARGE(R.string.Large, R.drawable.outline_coffee_30),
    EXTRA(R.string.Extra, R.drawable.outline_coffee_40)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeToggleButton(){
    val items = Sizes.entries.toTypedArray()

    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){
        items.forEach {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.width(120.dp).height(90.dp)){

                Column (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Icon(painter = painterResource(id = it.drawable), contentDescription = null,)

                    Text(text = stringResource(id = it.text),
                        style = MaterialTheme.typography.bodySmall)

                    Text(text = "480ml", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonPreview(){
    SizeToggleButton()
}