package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

}