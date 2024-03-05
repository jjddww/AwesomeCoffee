package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.flow.catch

class HomeRepository (private val apiHelper: ApiServiceHelper){
    fun getAdvertisementData() =
        apiHelper.getAdvertisementList()
            .catch {e ->
                Log.e("get Ads Api Error: ", e.toString())
            }

    fun getRecommendMenuData() =
        apiHelper.getRecommendedMenuList()
            .catch { e ->
                Log.e("get Recommended Menu Api Error:", e.toString())
            }

    fun getNewMenuData() = apiHelper.getNewMenuList()
        .catch { e ->
            Log.e("get New Menu Api Error", e.toString())
        }
}