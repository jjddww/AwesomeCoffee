package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.PaymentRepository

class PaymentViewModel(application: Application): ViewModel() {
    val repository: PaymentRepository
    var totalPrice =  MutableLiveData(0)

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = PaymentRepository(cartDao, application)
    }

    val items = repository.getCartList.asLiveData()

    fun getTotalPrice() {
        totalPrice.value = 0
        items.value?.forEach {
            totalPrice.value = totalPrice.value!! + it.price * it.amount
            if(it.shot)
                totalPrice.value = totalPrice.value!! + 500 * it.amount
        }
    }

    fun addItems(item: Cart){
        var price = (if(item.shot) 500 * item.amount else 0) + item.price
        repository.addItems(item.menuName, item.option, price.toDouble())
    }

    fun paymentTest(totalPrice: Int){
        repository.paymentTest(totalPrice.toDouble())
    }
}