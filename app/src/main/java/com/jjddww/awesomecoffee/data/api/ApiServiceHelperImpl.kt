package com.jjddww.awesomecoffee.data.api

import kotlinx.coroutines.flow.flow

class ApiServiceHelperImpl(private val apiService: ApiService): ApiServiceHelper {
    override fun getAdvertisementList() = flow{
        emit(apiService.getAdvertisementList())
    }

}