package com.jjddww.awesomecoffee.compose.payment

import android.app.Activity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.ui.theme.neutralVariant70
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.outlineLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.utilities.ApplyDecimalFormat
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import com.jjddww.awesomecoffee.viewmodels.PaymentViewModel
import okhttp3.internal.format

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    onPaymentSuccessScreen:() -> Unit
) {

    val items by viewModel.items.observeAsState(initial = emptyList())
    val totalPrice by viewModel.totalPrice.observeAsState(initial = 0)
    val activity = LocalContext.current as Activity
    val isSuccessPayment by viewModel.isSuccessPayment.observeAsState(initial = false)
    val scrollState = rememberScrollState()
    viewModel.getTotalPrice()


    val onClearSuccess = {
        viewModel.clearSuccessPayment()
    }


    val onAddItems = { items.forEach {
        viewModel.addItems(it)
    } }

    val onClearItems = {
        viewModel.clearItems()
    }

    val onRequestPayment = { totalPrice: Int, activity: Activity ->
        viewModel.paymentTest(totalPrice, activity)
    }

    val onRequestUpdateStamp = {
        viewModel.updateStamp()
    }


    if(isSuccessPayment) {
        onPaymentSuccessScreen()
        onClearSuccess()
        onRequestUpdateStamp()
    }

    
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)){
        Spacer(Modifier.height(35.dp))

        Text(text = stringResource(id = R.string.payment),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight
        )

        Spacer(Modifier.height(70.dp))

        Text(text = stringResource(id = R.string.use_coupon),
            style = MaterialTheme.typography.labelMedium,
            modifier= Modifier.padding(start = 20.dp))


        Spacer(Modifier.height(10.dp))


        Row(Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){


            Text(text = stringResource(id = R.string.empty_coupon),
                color = neutralVariant70,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 20.dp))


            Button(modifier = Modifier
                .width(130.dp)
                .height(40.dp)
                .padding(end = 20.dp),
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(outlineLight)){

                Text(
                    stringResource(id = R.string.select_coupon),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(Modifier.height(50.dp))


        Text(text = stringResource(id = R.string.order_list),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 20.dp))


        Spacer(Modifier.height(20.dp))


        LazyColumn(
            Modifier
                .fillMaxWidth()
                .height(300.dp)){

            items(items){
                PaymentListItem(it)
            }
        }

        Spacer(Modifier.height(30.dp))

        Text(text = "할인 금액: 0원",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleSmall)

        Text(text = format(stringResource(id = R.string.total_price_format), ApplyDecimalFormat(totalPrice)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, end = 20.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(20.dp))


        Row(
            Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom){

            PaymentButton(onClick = {
                onClearItems()
                onAddItems()
                onRequestPayment(totalPrice, activity)},
                color = onSurfaceVariantLight, text = stringResource(id = R.string.payment))
            PaymentButton(onClick = { /*TODO*/ }, color = surfaceVariant, text = stringResource(id = R.string.cancel))
        }

    }
}


@Composable
fun PaymentButton(onClick: () -> Unit, color: Color, text: String) {
    Button(onClick = { onClick() },
        modifier = Modifier
            .width(170.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = text)
    }
}


@Composable
fun PaymentListItem(item: Cart){
    Column(
        Modifier
            .fillMaxWidth()
            .height(120.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = rememberImagePainter(data = item.url),
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            Column(
                Modifier
                    .width(200.dp)
                    .padding(end = 10.dp)
            ) {
                Text(text = item.menuName, fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                    fontSize = 14.sp, color = Color.Black)
                Text(
                    text = item.option, modifier = Modifier.padding(top = 5.dp),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                    color = neutralVariant70, fontSize = 12.sp
                )

                if (item.shot)
                    Text(
                        text = stringResource(id = R.string.extra_shot),
                        modifier = Modifier.padding(top = 5.dp),
                        fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                        color = neutralVariant70,
                        fontSize = 12.sp
                    )
            }

            Column (
                Modifier
                    .width(100.dp)
                    .padding(end = 10.dp)){

                Text(
                    text = format(
                        stringResource(id = R.string.price_format),
                        ApplyDecimalFormat(item.price)
                    ),
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                    color = Color.Black, fontSize = 12.sp
                )

                Text(
                    text = "수량: ${item.amount}",
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                    color = Color.Black, fontSize = 12.sp
                )

                Text(
                    text = format(stringResource(id = R.string.total_price_format),
                        ApplyDecimalFormat((item.price + if(item.shot) EXTRA_SHOT_PRICE else 0) * item.amount)),
                    modifier = Modifier.padding(end = 10.dp),
                    fontFamily = FontFamily(Font(R.font.spoqahansansneo_regular)),
                    color = Color.Black, fontSize = 12.sp
                )

            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 5.dp, end = 5.dp, top = 20.dp),
            color = outlineDarkHighContrast
        )
    }
}
