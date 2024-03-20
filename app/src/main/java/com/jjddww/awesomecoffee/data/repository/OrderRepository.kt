package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.flow.catch

class OrderRepository(private val apiHelper: ApiServiceHelper) {

    fun getMenuList() =
        apiHelper.getMenuList()
            .catch {e ->
                Log.e("get Menu List Api Error", e.toString())
            }

}