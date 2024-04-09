package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.MemberInfo
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.flow.catch

class OrderHistoryRepository(private val apiHelper: ApiServiceHelper) {

    fun getOrderHistory() =
        apiHelper.getOrderList(MemberInfo.memberId?: 0L)
            .catch { e ->
                Log.e("get order history Api Error", e.toString())
            }

}