package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Coupon
import com.jjddww.awesomecoffee.data.model.MainCategory
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.model.SubCategory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/advertisement/all")
    suspend fun getAdvertisementList() : List<BannerAd>

    @GET("/api/menu/all")
    suspend fun getMenuList(): List <Menu>

    @GET("/api/menu/recommend")
    suspend fun getRecommendMenuList(): List<Menu>

    @GET("/api/menu/new")
    suspend fun getNewMenuList(): List<Menu>

    @GET("/api/menu/desc")
    suspend fun getMenuDescription(@Query("id") id: Int): List<Menu>

    @GET("/api/coupon/coupon_info")
    suspend fun getCouponList(): List<Coupon>

    @GET("/api/menu/search")
    suspend fun getMenuSearchResult(@Query("keyword") keyword: String): List<Menu>
}