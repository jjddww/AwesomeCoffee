package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.repository.LoginRepository

class LoginViewModel(private val activity: Activity): ViewModel() {

    val repository = LoginRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var isSuccessLogin = repository.isSuccessLogin.asLiveData()

    fun LoginKakao(){
        repository.LoginKakao(activity)
    }

    fun checkLoginToken(){
        repository.checkLoginToken()
    }

    fun logoutKakao(){
        repository.requestLogout()
    }
}