package com.jjddww.awesomecoffee.data

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/advertisement/all")
    suspend fun getAdvertisementList() : Response<ResponseBody>

//    @GET("/api/menu/all")
//    suspend fun getAllMenuList(): List <Menu>
//
//    @GET("/api/menu/recommend")
//    suspend fun getRecommendMenuList(): List<Menu>
//
//    @GET("/api/menu/new")
//    suspend fun getNewMenuList(): List<Menu>
//
//    @GET("/api/menu/desc")
//    suspend fun getMenuDescription(@Query("id") id: Int): MenuDescription
//
//    @GET("/api/category/main")
//    suspend fun getMainCategory(): List<String>

    @GET("/api/category/sub")
    suspend fun getSubCategory(@Query("main_category") mainCategory: String): List<String>
}