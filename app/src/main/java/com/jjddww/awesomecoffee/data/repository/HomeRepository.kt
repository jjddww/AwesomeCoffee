package com.jjddww.awesomecoffee.data.repository

import com.google.gson.Gson
import com.jjddww.awesomecoffee.data.Advertisement
import com.jjddww.awesomecoffee.data.ApiService
import com.jjddww.awesomecoffee.data.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository {
    private val service = RetrofitClient.retrofit

    suspend fun getAdvertisementData() : Flow<List<Advertisement>> = flow {
        val response = service.getAdvertisementList()

        if(response.isSuccessful){
        }
    }

}