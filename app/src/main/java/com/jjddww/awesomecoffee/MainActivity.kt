package com.jjddww.awesomecoffee

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.jjddww.awesomecoffee.compose.AwesomeCoffeeApp
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwesomeCoffeeTheme(darkTheme = false) {
                Surface(color = surfaceVariantLight) {
                    AwesomeCoffeeApp()
                }
            }
        }
        KakaoSdk.init(application, "29b9b952f0e3a95b8c07119550dcc484")

        Log.d("Main", "keyhash : ${Utility.getKeyHash(this)}")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
    }
}
