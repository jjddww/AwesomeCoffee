package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.PaymentRepository
import com.jjddww.awesomecoffee.utilities.COUPON_NOT_EMPTY
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application): ViewModel() {
    val repository: PaymentRepository
    val items: LiveData<List<Cart>>

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = PaymentRepository(true, cartDao, application, ApiServiceHelperImpl(RetrofitClient.retrofit))
        items = repository.getCartList.asLiveData()
    }

    val isSuccessPayment = repository.isSuccessPayment.asLiveData()
    var couponList = repository.getCouponList().asLiveData()
    val couponId = MutableLiveData(0)
    val couponType = MutableLiveData("")
    val discountAmount = MutableLiveData(0)
    val checkApplyDiscount = MutableLiveData(false)
    var couponNotice = MutableLiveData(COUPON_NOT_EMPTY)
    var totalPrice = mutableStateOf(0)



    fun getTotalPriceValue(): Int {
        totalPrice.value = 0
        items.value?.forEach {
            totalPrice.value += it.price * it.amount
            if(it.shot)
                totalPrice.value += EXTRA_SHOT_PRICE * it.amount
        }
        if(couponId.value != 0)
            totalPrice.value = totalPrice.value - discountAmount.value!!

        if(totalPrice.value < 0)
            totalPrice.value = 0
        return totalPrice.value
    }


    fun setCouponInfo(id: Int, name: String, type: String, discount: Int){
        couponId.value = id
        couponType.value = type
        discountAmount.value = discount
        couponNotice.value = name
        repository.settingCouponId(couponId.value!!)

        if(couponType.value == "deduction") {
            if(totalPrice.value - discount < 0)
                totalPrice.value = 0
            else
                totalPrice.value = totalPrice.value - discount
        }
        else
            totalPrice.value = totalPrice.value * discount
    }


    fun clearSuccessPayment(){
        repository.clearIsSuccessPayment()
    }

    fun addItems(item: Cart){
        var price = (if(item.shot) EXTRA_SHOT_PRICE else 0) + item.price
        if(couponId.value != 0 && !checkApplyDiscount.value!!){
            if(item.amount > 1)
            {
                repository.addItems(item.menuName, item.option, (price - discountAmount.value!!).toDouble(), 1)
                repository.addItems(item.menuName, item.option, price.toDouble(), item.amount - 1)
            }
            else{
                repository.addItems(item.menuName, item.option, (price - discountAmount.value!!).toDouble(), 1)
            }
            checkApplyDiscount.value = true
        }
        else{
            repository.addItems(item.menuName, item.option, price.toDouble(), item.amount)
        }
        Log.e("boot pay", "$price")
    }

    fun clearItems(){
        repository.clearItems()
    }

    fun paymentTest(totalPrice: Int, activity: Activity){
        Log.e("boot pay", "$totalPrice")
        repository.paymentTest(totalPrice.toDouble(), activity)
    }

    fun useCoupon(){
        repository.useCoupon()
    }
}