package com.jjddww.awesomecoffee.compose.payment

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.ui.theme.onSecondaryLight
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight

@Composable
fun CouponBottomSheet(
    couponList: List<Coupon>,
    onSettingCouponInfo: (Int, String ,Int) -> Unit){

    var selectedOption = remember { mutableStateOf(0) }

    var items = couponList.toMutableList()
    items.add(0, Coupon())


    Column(
        Modifier
            .fillMaxSize()
            .background(onSecondaryLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            text = stringResource(id = R.string.select_coupon),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        )

        items.forEachIndexed { index, coupon ->
            if(index == 0){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            if (index != selectedOption.value)
                                selectedOption.value = index
                            onSettingCouponInfo(0, "", 0)
                        },
                    verticalAlignment = Alignment.CenterVertically){

                    RadioButton(selected = (index == selectedOption.value),
                        onClick = {
                            if(index != selectedOption.value)
                                selectedOption.value = index
                            onSettingCouponInfo(0, "", 0)
                        },
                        modifier = Modifier.padding(start = 10.dp))

                    Text(text = stringResource(id = R.string.no_use_coupon), style = MaterialTheme.typography.labelMedium)
                }
            }
            else{
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            if (index != selectedOption.value)
                                selectedOption.value = index
                            onSettingCouponInfo(coupon.id, coupon.type, coupon.discount)
                        },
                    verticalAlignment = Alignment.CenterVertically){

                    RadioButton(selected = (index == selectedOption.value),
                        onClick = {
                            if(index != selectedOption.value)
                                selectedOption.value = index
                            onSettingCouponInfo(coupon.id, coupon.type, coupon.discount)
                        },
                        modifier = Modifier.padding(start = 10.dp))

                    Column(
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.Center) {
                        Text(text = coupon.name, style = MaterialTheme.typography.labelMedium)
                        Text(text = "기한: ${coupon.date}까지", style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 3.5.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {},
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(onSurfaceVariantLight)
        ) {
            Text(text = stringResource(id = R.string.finish_select))
        }

        Spacer(modifier = Modifier.weight(0.5f))
    }
}