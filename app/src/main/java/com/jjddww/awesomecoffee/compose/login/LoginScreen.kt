package com.jjddww.awesomecoffee.compose.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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

    if(isSuccessLogin){
        onHomeScreen()
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

        Spacer(Modifier.height(50.dp))

        LoginTextField(stringResource(id = R.string.id_placeholder))
        LoginTextField(stringResource(id = R.string.password_placeholder))

        Spacer(modifier = Modifier.height(56.dp))

        Button(
            modifier = Modifier.size(213.dp, 40.dp),
            onClick = { requestLogin() },
            colors = ButtonDefaults.buttonColors(
                primaryLight
            )) {
            Text(
                stringResource(id = R.string.login),
                style = MaterialTheme.typography.labelMedium)
        }

//        Row(Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center){
//            TextButton(string = stringResource(id = R.string.join))
//            TextButton(string = stringResource(id = R.string.find_id))
//            TextButton(string = stringResource(id = R.string.find_password))
//        }
    }
}

@Composable
fun TextButton(string: String){

    Button(onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            Color.Transparent
        )) {
        Text(text = string,
            style = MaterialTheme.typography.labelSmall,
            color = primaryLight)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(placeholder: String){
    var textFieldState by remember { mutableStateOf("") }
    val onTextChange = { text: String ->
        textFieldState = text
    }

    TextField(value = textFieldState,
        onValueChange = onTextChange,
        placeholder = {Text(placeholder,
            style = MaterialTheme.typography.labelSmall)},
        colors = TextFieldDefaults.textFieldColors
            (containerColor = Color.Transparent,
            unfocusedPlaceholderColor = surfaceVariant,
            unfocusedIndicatorColor = surfaceVariant,
            focusedIndicatorColor = onSurfaceVariantLight,
            focusedTextColor = onSurfaceVariantLight))
}
