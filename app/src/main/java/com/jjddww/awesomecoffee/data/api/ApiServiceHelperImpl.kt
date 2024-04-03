package com.jjddww.awesomecoffee.data.api

import android.util.Log
import com.jjddww.awesomecoffee.data.model.CommonResponse
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiServiceHelperImpl(private val apiService: ApiService): ApiServiceHelper {
    override fun getAdvertisementList() = flow{
        emit(apiService.getAdvertisementList())
    }

    override fun getRecommendedMenuList(): Flow<List<Menu>>  = flow{
        emit(apiService.getRecommendMenuList())
    }

    override fun getNewMenuList(): Flow<List<Menu>> = flow {
        emit(apiService.getNewMenuList())
    }

    override fun getMenuDescription(id: Int): Flow<List<Menu>> = flow{
        emit(apiService.getMenuDescription(id))
    }

    override fun getMenuList(): Flow<List<Menu>>  = flow {
        emit(apiService.getMenuList())
    }

    override fun getCouponList(): Flow<List<Coupon>> = flow{
        emit(apiService.getCouponList())
    }

    override fun getSearchResult(keyword: String): Flow<List<Menu>> = flow{
        emit(apiService.getMenuSearchResult(keyword))
    }

    override fun sendMemberId(id: String){
        CoroutineScope(Dispatchers.IO).launch{
            apiService.sendMemberId(id)
        }
    }

    override fun getStampCount(id: String): Flow<Int> = flow{
        emit(apiService.getStampCount(id).toInt())
    }

    override fun updateStamp(id: Long, qty: Int){
        CoroutineScope(Dispatchers.IO).launch {
            apiService.updateStamp(id, qty)
        }
    }

}