package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.repository.LoginRepository

class LoginViewModel(private val activity: Activity): ViewModel() {

    val repository = LoginRepository()

    var isSuccessLogin = repository.isSuccessLogin.asLiveData()

    fun LoginKakao(){
        repository.LoginKakao(activity)
    }
}