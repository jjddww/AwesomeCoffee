package com.jjddww.awesomecoffee.compose.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.data.model.Order
import com.jjddww.awesomecoffee.ui.theme.lightGray
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.OrderHistoryViewModel

@Composable
fun OrderHistoryScreen(viewModel: OrderHistoryViewModel){
    val orderList by viewModel.orderHistory.observeAsState(initial = emptyList())

    Column(Modifier.fillMaxSize()) {
        Spacer(Modifier.height(35.dp))

        Text(text = stringResource(id = R.string.order_list),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight
        )

        Spacer(Modifier.height(70.dp))

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)){
            items(orderList) {
                HistoryItem(history = it)
            }
        }
    }
}

@Composable
fun HistoryItem(history: Order){
    var option = history.option.replace("\n", "")

    Row(
        Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(start = 20.dp)){

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center) {

            Text(text = history.menuName,
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 14.sp,
                color = Color.Black)

            Spacer(modifier = Modifier.height(3.dp))

            Text(text = "옵션: $option | 수량: ${history.quantity}",
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 12.sp,
                color = Color.Gray)

            Spacer(modifier = Modifier.height(3.dp))

            Text(text = "구매일자: ${history.date}",
                fontFamily = FontFamily(Font(R.font.spoqahansansneo_medium)),
                fontSize = 12.sp,
                color = Color.Gray)

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(bottom = 7.dp),
                color = outlineDarkHighContrast
            )
        }
    }
}