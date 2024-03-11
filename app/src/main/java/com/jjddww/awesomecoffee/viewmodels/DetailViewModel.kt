package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.DetailRepository

class DetailViewModel(var id: Int): ViewModel() {

    var repository = DetailRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    val description: LiveData<List<Menu>> = repository.getMenuDescription(id).asLiveData()


    var totalAmount = MutableLiveData(1)

    fun setAmount(){
        totalAmount.value = 1
    }

    fun increaseAmount(){
        totalAmount.value = totalAmount.value!! + 1
    }

    fun decreaseAmount(){
        if (totalAmount.value!! > 1){
            totalAmount.value = totalAmount.value!! - 1
        }
    }
}