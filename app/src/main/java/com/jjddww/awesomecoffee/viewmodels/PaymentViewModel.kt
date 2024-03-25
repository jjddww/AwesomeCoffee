package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.PaymentRepository
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application): ViewModel() {
    val repository: PaymentRepository
    var totalPrice =  MutableLiveData(0)

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = PaymentRepository(cartDao, application)
    }

    val items = repository.getCartList.asLiveData()
    val isSuccessPayment = repository.isSuccessPayment.asLiveData()


    fun getTotalPrice() {
        totalPrice.value = 0
        items.value?.forEach {
            totalPrice.value = totalPrice.value!! + it.price * it.amount
            if(it.shot)
                totalPrice.value = totalPrice.value!! + EXTRA_SHOT_PRICE * it.amount
        }
    }

    fun clearSuccessPayment(){
        repository.clearIsSuccessPayment()
    }

    fun addItems(item: Cart){
        var price = (if(item.shot) EXTRA_SHOT_PRICE * item.amount else 0) + item.price
        repository.addItems(item.menuName, item.option, price.toDouble(), item.amount)
    }

    fun clearItems(){
        repository.clearItems()
    }

    fun paymentTest(totalPrice: Int, activity: Activity){
        repository.paymentTest(totalPrice.toDouble(), activity)
    }
}