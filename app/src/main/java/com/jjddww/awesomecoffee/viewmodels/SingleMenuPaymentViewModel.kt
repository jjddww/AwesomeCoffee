package com.jjddww.awesomecoffee.viewmodels

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.PaymentRepository
import com.jjddww.awesomecoffee.utilities.COUPON_NOT_EMPTY
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleMenuPaymentViewModel(
    application: Application,
    menu: Menu?,
    option: String,
    amount: Int,
    isShot: Boolean): ViewModel() {
    val repository: PaymentRepository
    var menuData = MutableLiveData<Menu?>(menu)
    var optionData = MutableLiveData(option)
    var qty = MutableLiveData(amount)
    var extraShot = MutableLiveData(isShot)
    var totalPrice: MutableState<Int> =
        mutableStateOf(menu?.price!! * qty.value!! + if(isShot) EXTRA_SHOT_PRICE * qty.value!! else 0)

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = PaymentRepository(cartDao, application, ApiServiceHelperImpl(RetrofitClient.retrofit))
    }

    val isSuccessPayment = repository.isSuccessPayment.asLiveData()
    var couponList = repository.getCouponList().asLiveData()
    val couponId = MutableLiveData(0)
    val couponType = MutableLiveData("")
    val discountAmount = MutableLiveData(0)
    var couponNotice = MutableLiveData(COUPON_NOT_EMPTY)


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


    fun addItems(){
        val price = (if(extraShot.value == true) EXTRA_SHOT_PRICE else 0) +
                (menuData.value?.price ?: 0)

        if(couponType.value == "deduction"){
            if(couponId.value != 0){ //쿠폰이 적용됐을 때
                if(qty.value!! > 1){
                    repository.addItems(menuData.value!!.menuName, optionData.value!!, (price - discountAmount.value!!).toDouble(), 1)
                    repository.addItems(menuData.value!!.menuName, optionData.value!!, price.toDouble(), qty.value!! - 1)
                }
                else
                    repository.addItems(menuData.value!!.menuName, optionData.value!!, (price - discountAmount.value!!).toDouble(), 1)
            }
        }
        else if(couponType.value == "percentage"){
            repository.addItems(menuData.value!!.menuName, optionData.value!!, (price * discountAmount.value!!).toDouble(), qty.value!!)
        }
        else{
            repository.addItems(menuData.value!!.menuName, optionData.value!!, price.toDouble(), qty.value!!)
        }
    }


    fun clearItems(){
        repository.clearItems()
    }


    fun paymentTest(totalPrice: Int, activity: Activity){
        repository.paymentTest(totalPrice.toDouble(), activity)
    }

    fun useCoupon(){
        repository.useCoupon()
    }
}