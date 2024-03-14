package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.MainCategory
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.model.SubCategory
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

    override fun getMenuDescription(id: Int): Flow<List<Menu>> = flow{
        emit(apiService.getMenuDescription(id))
    }

    override fun getMainCategory(): Flow<List<MainCategory>>  = flow{
        emit(apiService.getMainCategory())
    }

    override fun getSubCategory(category: String): Flow<List<SubCategory>>  = flow {
        emit(apiService.getSubCategory(category))
    }

    override fun getMenuList(): Flow<List<Menu>>  = flow {
        emit(apiService.getMenuList())
    }

}