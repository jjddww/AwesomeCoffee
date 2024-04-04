package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.CommonResponse
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ApiServiceHelper {
    fun getAdvertisementList(): Flow<List<BannerAd>>

    fun getRecommendedMenuList(): Flow<List<Menu>>

    fun getNewMenuList(): Flow<List<Menu>>

    fun getMenuDescription(id: Int): Flow<List<Menu>>

    fun getMenuList(): Flow<List<Menu>>

    fun getCouponList(): Flow<List<Coupon>>

    fun getSearchResult(keyword: String): Flow<List<Menu>>

    fun sendMemberId(id: String): Unit

    fun getStampCount(id: String): Flow<Int>

    fun updateStamp(id: Long, qty: Int)

    fun deleteUsedCoupon(couponId: Int, memberId: Long)
}