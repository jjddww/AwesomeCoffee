package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Menu
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/advertisement/all")
    suspend fun getAdvertisementList() : List<BannerAd>

    @GET("/api/menu/all")
    suspend fun getAllMenuList(): List <Menu>

    @GET("/api/menu/recommend")
    suspend fun getRecommendMenuList(): List<Menu>

    @GET("/api/menu/new")
    suspend fun getNewMenuList(): List<Menu>

//    @GET("/api/menu/desc")
//    suspend fun getMenuDescription(@Query("id") id: Int): MenuDescription

    @GET("/api/category/main")
    suspend fun getMainCategory(): List<String>

    @GET("/api/category/sub")
    suspend fun getSubCategory(@Query("main_category") mainCategory: String): List<String>
}