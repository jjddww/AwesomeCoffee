package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow

interface ApiServiceHelper {
    fun getAdvertisementList(): Flow<List<BannerAd>>

    fun getRecommendedMenuList(): Flow<List<Menu>>

    fun getNewMenuList(): Flow<List<Menu>>

}