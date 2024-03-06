package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class DetailRepository (private val apiHelper: ApiServiceHelper) {
    fun getMenuDescription(id: Int): Flow<List<Menu>> =
        apiHelper.getMenuDescription(id)
            .catch { e->
                Log.e("get menu desc Api Error", e.toString())
            }
}