package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.PaymentRepository
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE

class SingleMenuPaymentViewModel(application: Application): ViewModel() {
    val repository: PaymentRepository
    var totalPrice =  MutableLiveData(0)

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = PaymentRepository(cartDao, application)
    }

    val isSuccessPayment = repository.isSuccessPayment.asLiveData()

    val menuData = MutableLiveData<Menu>()
    val optionData = MutableLiveData<String>("")
    val qty = MutableLiveData<Int>(0)
    val extraShot = MutableLiveData<Boolean>()

    fun setCart(menu: Menu, option: String, amount: Int, isShot: Boolean){
        totalPrice.value = 0
        menuData.value = menu
        optionData.value = option
        qty.value = amount
        extraShot.value = isShot
        totalPrice.value = menu.price * amount + if(isShot) EXTRA_SHOT_PRICE * amount else 0
    }

    fun clearSuccessPayment(){
        repository.clearIsSuccessPayment()
    }

    fun addItems(){
        val price = (if(extraShot.value == true) EXTRA_SHOT_PRICE * qty.value!! else 0) +
                (menuData.value?.price ?: 0)
        repository.addItems(menuData.value!!.menuName, optionData.value!!, price.toDouble(), qty.value!!)
    }

    fun clearItems(){
        repository.clearItems()
    }

    fun paymentTest(totalPrice: Int, activity: Activity){
        repository.paymentTest(totalPrice.toDouble(), activity)
    }
}