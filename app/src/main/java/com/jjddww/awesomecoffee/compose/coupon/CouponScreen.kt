package com.jjddww.awesomecoffee.compose.coupon

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.ui.theme.neutralVariant70
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.CouponViewModel
import java.text.SimpleDateFormat



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CouponScreen(
    viewModel: CouponViewModel,
    navController: NavController,
    onNavigateRoute: (String) -> Unit){

    val couponList by viewModel.couponList.observeAsState(emptyList())

    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute) },
        containerColor = surfaceVariantLight
    ){

        Column(modifier = Modifier
            .fillMaxSize()) {

            Spacer(Modifier.height(35.dp))

            Text(text = stringResource(id = R.string.coupon),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = tertiaryLight
            )

            Spacer(modifier = Modifier.height(50.dp))


            LazyColumn(modifier= Modifier.fillMaxSize()){
                items(couponList){
                    CouponItemView(coupon = it)
                }
            }
        }
    }
}

@Composable
fun CouponItemView(coupon: Coupon){

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)){

            Image(painter = painterResource(id = R.drawable.outline_local_activity_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .align(Alignment.CenterVertically))

            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 25.dp)) {

                Text(text = coupon.name, style = MaterialTheme.typography.titleSmall)

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "기한: ${coupon.date}까지", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 10.dp, end = 10.dp),
            color = outlineDarkHighContrast
        )
    }
}
