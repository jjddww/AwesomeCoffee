package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
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

    var showBottomSheet = MutableLiveData(false)

    var isIced = MutableLiveData<Temperature>(Temperature.HOT)

    var cupSize = MutableLiveData<Sizes>(Sizes.REGULAR)

    var cup = MutableLiveData<String>(PERSONAL_CUP)

    var takeout = MutableLiveData<String>(TOGO)

    var extraShot = MutableLiveData<Boolean>(false)


    fun addCartItem(menu: Menu, mainCategory: String) {
        val item =
            if (mainCategory == BEVERAGE) {
                var option = "${isIced.value} | ${cupSize.value} | ${cup.value}"
                totalAmount.value?.let { amount ->
                    extraShot.value?.let { isExtraShot ->
                        Cart(menu.imgUrl, menu.menuName, menu.price, option, isExtraShot, amount)
                    }
                }
            } else {
                var option = takeout.value

                totalAmount.value?.let { amount ->
                    extraShot.value?.let { isExtraShot ->
                        option?.let { option ->
                            Cart(menu.imgUrl, menu.menuName, menu.price,
                                option, isExtraShot, amount)
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

        changeBottomSheetState()
    }




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
        extraShot.value = true
        totalPrice.value = totalPrice.value?.plus(EXTRA_SHOT_PRICE)
    }

    fun leaveOutShot(){
        extraShot.value = false
        totalPrice.value = totalPrice.value?.minus(EXTRA_SHOT_PRICE)
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

        if(option is Sizes)
            cupSize.value = option

        if(option is String)
            cup.value = option.toString()
    }

    fun settingDessertOptions(option: String){
        takeout.value = option
    }
}