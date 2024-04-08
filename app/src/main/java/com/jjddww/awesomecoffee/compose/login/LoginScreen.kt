package com.jjddww.awesomecoffee.compose.login

import android.app.Activity
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jjddww.awesomecoffee.AppNavController
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.compose.AwesomeCoffeeApp
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.onSurfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.primaryLight
import com.jjddww.awesomecoffee.ui.theme.surfaceVariant
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight
import com.jjddww.awesomecoffee.viewmodels.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel,
                onHomeScreen:() -> Unit){

    val isSuccessLogin by viewModel.isSuccessLogin.observeAsState(initial = false)

    val requestLogin = { viewModel.LoginKakao() }

    val checkLoginToken = { viewModel.checkLoginToken() }


    if(isSuccessLogin){
        onHomeScreen()
    }
    else{
        checkLoginToken()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(Modifier.height(164.dp))

        Image(
            modifier = Modifier,
            painter = painterResource(R.drawable.logo_login),
            contentDescription = null)

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.login_app_name),
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
            color = tertiaryLight)

        Spacer(Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.kakao_login_button),
            contentDescription = "",
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
                .clickable {
                requestLogin() })

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    LoginScreen(viewModel = LoginViewModel(LocalContext.current as Activity)) {
        
    }
}


