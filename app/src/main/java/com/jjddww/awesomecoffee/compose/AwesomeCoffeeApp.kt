package com.jjddww.awesomecoffee.compose

import android.app.Application
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jjddww.awesomecoffee.compose.home.HomeScreen
import com.jjddww.awesomecoffee.ui.theme.AwesomeCoffeeTheme
import com.jjddww.awesomecoffee.ui.theme.surfaceVariantLight
import com.jjddww.awesomecoffee.viewmodels.AdImageUrlListViewModel

@Composable
fun AwesomeCoffeeApp(){
    AwesomeCoffeeTheme {
        Surface (color = surfaceVariantLight){
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: AdImageUrlListViewModel = viewModel(
                    it,
                    "AdImageUrlListViewModel",
                    HomeViewModelFactory(
                        LocalContext.current.applicationContext as Application
                    )
                )
                HomeScreen(viewModel)
            }
        }
    }
}

@SuppressWarnings("unchecked")
class HomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdImageUrlListViewModel::class.java))
            return AdImageUrlListViewModel(application) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}