package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.compose.order.Cups
import com.jjddww.awesomecoffee.compose.order.Sizes
import com.jjddww.awesomecoffee.compose.order.Temperature
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.DetailRepository
import com.jjddww.awesomecoffee.utilities.BEVERAGE
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import com.jjddww.awesomecoffee.utilities.PERSONAL_CUP
import com.jjddww.awesomecoffee.utilities.TOGO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(application: Application, var id: Int): ViewModel() {

    val repository: DetailRepository


    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = DetailRepository(ApiServiceHelperImpl(RetrofitClient.retrofit), cartDao)
    }


    val description: LiveData<List<Menu>> = repository.getMenuDescription(id).asLiveData()

    var totalAmount = MutableLiveData(1)

    var totalPrice = MutableLiveData(0)

    var menuPrice = 0

    var showBottomSheet = MutableLiveData(false)

    var isIced = MutableLiveData<Temperature>(Temperature.HOT)

    var cupSize = MutableLiveData<Sizes>(Sizes.REGULAR)

    var cup = MutableLiveData<String>(PERSONAL_CUP)

    var takeout = MutableLiveData<String>(TOGO)

    var extraShot = MutableLiveData<Boolean>(false)

    var beverageOption = MutableLiveData<String>("${isIced.value} | ${cupSize.value!!.text}" +
            " | ${cup.value}")
    var dessertOption = MutableLiveData<String>("${takeout.value}")


    fun setBeverageOption(){
        beverageOption.value = "${isIced.value!!.name} | ${cupSize.value!!.text} | ${cup.value}"
    }

    fun setDessertOption(){
        dessertOption.value = "${takeout.value}"
    }


    fun addCartItem(menu: Menu, mainCategory: String) {
        val item =
            if (mainCategory == BEVERAGE) {
                var option = "${isIced.value} | ${cupSize.value!!.text} | ${cup.value}"
                totalAmount.value?.let { amount ->
                    extraShot.value?.let { isExtraShot ->
                        Cart(menu.imgUrl, menu.menuName, menu.price, option, isExtraShot, amount, false)
                    }
                }
            } else {
                var option = takeout.value

                totalAmount.value?.let { amount ->
                    extraShot.value?.let { isExtraShot ->
                        option?.let { option ->
                            Cart(menu.imgUrl, menu.menuName, menu.price,
                                option, isExtraShot, amount, false)
                        }
                    }
                }
            }

        viewModelScope.launch(Dispatchers.IO) {
            val findDuplicateItemAmount: Int? =
                repository.findSameItem(item!!.menuName, item.option, item.shot).first()

            if (findDuplicateItemAmount != null && findDuplicateItemAmount > 0) {
                item.amount += findDuplicateItemAmount
            }

            repository.addCartItem(item)
        }
    }




    fun setAmount(){
        totalAmount.value = 1
    }

    fun increaseAmount(){
        totalAmount.value = totalAmount.value!! + 1
        totalPrice.value = ((menuPrice + cupSize.value!!.extraPrice)+ if(extraShot.value!!)EXTRA_SHOT_PRICE else 0) * totalAmount.value!!
    }

    fun decreaseAmount(){
        if (totalAmount.value!! > 1){
            totalAmount.value = totalAmount.value!! - 1
            totalPrice.value = ((menuPrice + cupSize.value!!.extraPrice) + if(extraShot.value!!)EXTRA_SHOT_PRICE else 0) * totalAmount.value!!
        }
    }

    fun setPrice(price: Int){
        totalPrice.value = totalAmount.value?.times(price)
        menuPrice = price
    }

    fun extraShot(){
        extraShot.value = true
        totalPrice.value = (menuPrice + EXTRA_SHOT_PRICE + cupSize.value!!.extraPrice) * totalAmount.value!!
    }

    fun leaveOutShot(){
        extraShot.value = false
        totalPrice.value = (menuPrice + cupSize.value!!.extraPrice) * totalAmount.value!!
    }

    fun changeBottomSheetState(){
        showBottomSheet.value = !showBottomSheet.value!!
        isIced.value = Temperature.HOT
        cupSize.value = Sizes.REGULAR
        cup.value = PERSONAL_CUP
        takeout.value = TOGO
        extraShot.value = false
    }

    fun settingBeverageOptions(option: Any){
        if(option is Temperature)
            isIced.value = option

        if(option is Sizes) {
            cupSize.value = option
            totalPrice.value = ((menuPrice + cupSize.value!!.extraPrice)+ if(extraShot.value!!)EXTRA_SHOT_PRICE else 0) * totalAmount.value!!
        }

        if(option is String)
            cup.value = option.toString()

        setBeverageOption()
    }

    fun settingDessertOptions(option: String){
        takeout.value = option

        setDessertOption()
    }
}