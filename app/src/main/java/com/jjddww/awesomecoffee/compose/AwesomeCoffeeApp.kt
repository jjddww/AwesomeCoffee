package com.jjddww.awesomecoffee.compose

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jjddww.awesomecoffee.compose.home.HomeScreen
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.viewmodels.AdImageUrlListViewModel
import com.jjddww.awesomecoffee.viewmodels.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AwesomeCoffeeApp() {
    val awesomeCoffeeNavController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomBar(Sections.values(), awesomeCoffeeNavController) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {
            AppNavGraph(navController = awesomeCoffeeNavController)
        }
    }
}