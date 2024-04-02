package com.jjddww.awesomecoffee.compose.payment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight

@Composable
fun PaymentSuccessScreen(onHomeScreen:() -> Unit) {

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(Modifier.height(60.dp))

        Text(text = stringResource(id = R.string.complete_payment),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight
        )

        Spacer(Modifier.height(40.dp))

        Text(text = stringResource(id = R.string.success_payment),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)

        Spacer(Modifier.height(40.dp))

        Button(onClick = {onHomeScreen()},
            modifier = Modifier
                .width(170.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(surfaceVariant)
        ) {
            Text(text = "홈으로 이동")
        }
    }
}