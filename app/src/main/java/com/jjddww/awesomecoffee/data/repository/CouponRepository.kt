package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.jjddww.awesomecoffee.data.model.Coupon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class CouponRepository(private val apiHelper: ApiServiceHelper) {

    fun getCouponList(): Flow<List<Coupon>> =
        apiHelper.getCouponList()
            .catch {e ->
                Log.e("get coupon Api Error", e.toString())
            }
}