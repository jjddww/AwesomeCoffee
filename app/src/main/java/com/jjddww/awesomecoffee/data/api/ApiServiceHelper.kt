package com.jjddww.awesomecoffee.data.api

import com.jjddww.awesomecoffee.data.model.BannerAd
import kotlinx.coroutines.flow.Flow

interface ApiServiceHelper {
    fun getAdvertisementList(): Flow<List<BannerAd>>

}