package com.jjddww.awesomecoffee.compose.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jjddww.awesomecoffee.R
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.notosans
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.ui.theme.tertiaryLight


@Composable
fun CustomTextField(textFieldState: String){
    TextField(value = textFieldState,
        onValueChange = {},
        placeholder = {Text("아이디")})
}


@Composable
fun LoginScreen(){
    var textFieldState by remember { mutableStateOf("") }

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

        Spacer(Modifier.height(50.dp))

        CustomTextField(textFieldState = textFieldState)
        CustomTextField(textFieldState = textFieldState)
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview(){
    AwesomeCoffeeTheme {
        Surface (color = surfaceVariantLight){
            LoginScreen()
        }
    }
}
