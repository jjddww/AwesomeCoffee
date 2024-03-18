package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.repository.CouponRepository

class CouponViewModel: ViewModel() {

    private val repository = CouponRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var couponList = repository.getCouponList().asLiveData()

}