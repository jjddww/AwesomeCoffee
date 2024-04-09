package com.jjddww.awesomecoffee.compose.other

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AppBottomBar
import com.jjddww.awesomecoffee.compose.coupon.CouponItemView
import com.jjddww.awesomecoffee.ui.theme.lightGray
import com.jjddww.awesomecoffee.ui.theme.outlineDarkHighContrast
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.LoginViewModel

enum class OtherItems (
    @DrawableRes val img: Int,
    val text: String
){
    HISTORY(R.drawable.baseline_receipt_long_24, "주문내역"),
    LOGOUT(R.drawable.baseline_logout_24, "로그아웃")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtherScreen(navController: NavController,
                onNavigateRoute: (String) -> Unit,
                viewModel: LoginViewModel,
                onLoginScreen: () -> Unit,
                onOrderHistoryScreen:()-> Unit){

    val showDialog = remember { mutableStateOf(false) }
    val isSuccessLogin by viewModel.isSuccessLogin.observeAsState(true)

    val onChangeShowDialog = {
        showDialog.value = true
    }

    val requestLogout = {
        viewModel.logoutKakao()
    }


    Scaffold(bottomBar = { AppBottomBar(navController, onNavigateRoute) },
        containerColor = surfaceVariantLight){

        Column (
            modifier = Modifier.fillMaxWidth()
        ){

            Spacer(Modifier.height(35.dp))

            Text(text = stringResource(id = R.string.other_screen),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = tertiaryLight
            )

            Spacer(Modifier.height(70.dp))

            LazyColumn(modifier= Modifier.fillMaxSize()){
                items(OtherItems.entries.size){
                    when(it){

                        OtherItems.HISTORY.ordinal -> OtherItemBoxLayout(OtherItems.entries[it].img, OtherItems.entries[it].text, onOrderHistoryScreen)

                        OtherItems.LOGOUT.ordinal -> OtherItemBoxLayout(OtherItems.entries[it].img, OtherItems.entries[it].text, onChangeShowDialog)
                    }
                }
            }

        }

        if (showDialog.value) {
            AlertDialogExample(showDialog, isSuccessLogin, requestLogout, onLoginScreen)
        }
    }
}


@Composable
fun OtherItemBoxLayout(
    @DrawableRes img: Int,
    text: String,
    onClickAction:() -> Unit
){

    Column (Modifier.clickable {
        onClickAction()
    }){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)){

            Image(painter = painterResource(id = img),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .align(Alignment.CenterVertically))

            Text(text = text,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp))
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

@Composable
fun AlertDialogExample(
    showDialog: MutableState<Boolean>,
    isSuccessLogin: Boolean,
    requestLogout: () -> Unit,
    onLoginScreen: () -> Unit
) {

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { showDialog.value = false },
        text = { Text(text = "로그아웃 하시겠습니까?") },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    requestLogout()
                    if(!isSuccessLogin)
                        onLoginScreen()
                }) {
                Text("확인")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showDialog.value = false
                }) {
                Text("취소")
            }
        }
    )
}
