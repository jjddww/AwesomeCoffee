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

    private val repository = DetailRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    val description: LiveData<List<Menu>> = repository.getMenuDescription(id).asLiveData()

    var totalAmount = MutableLiveData(1)

    var totalPrice = MutableLiveData(0)

    var showBottomSheet = MutableLiveData(false)

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

    fun setPrice(price: Int){
        totalPrice.value = totalAmount.value?.times(price)
    }

    fun extraShot(){
        totalPrice.value = totalPrice.value?.plus(500)
    }

    fun leaveOutShot(){
        totalPrice.value = totalPrice.value?.minus(500)
    }

    fun changeBottomSheetState(){
        showBottomSheet.value = !showBottomSheet.value!!
    }
}