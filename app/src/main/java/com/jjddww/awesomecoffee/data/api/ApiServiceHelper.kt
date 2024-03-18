package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.data.model.MainCategory
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.model.SubCategory
import kotlinx.coroutines.flow.Flow

interface ApiServiceHelper {
    fun getAdvertisementList(): Flow<List<BannerAd>>

    fun getRecommendedMenuList(): Flow<List<Menu>>

    fun getNewMenuList(): Flow<List<Menu>>

    fun getMenuDescription(id: Int): Flow<List<Menu>>

    fun getMenuList(): Flow<List<Menu>>

    fun getCouponList(): Flow<List<Coupon>>

}