package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.BannerAd
import com.jjddww.awesomecoffee.data.repository.HomeRepository


class HomeViewModel: ViewModel() {

    var repository = HomeRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))
    val advertisements : LiveData<List<BannerAd>> = repository.getAdvertisementData().asLiveData()

}