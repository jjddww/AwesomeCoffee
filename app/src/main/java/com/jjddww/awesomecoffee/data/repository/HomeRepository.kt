package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.flow.catch

class HomeRepository (private val apiHelper: ApiServiceHelper){
    fun getAdvertisementData() =
        apiHelper.getAdvertisementList()
            .catch {e ->
                Log.e("Api Error: ", e.toString())
            }

}