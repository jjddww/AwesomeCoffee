package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jjddww.awesomecoffee.compose.order.Sizes
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.CartRepository
import com.jjddww.awesomecoffee.utilities.EXTRA_SHOT_PRICE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(application: Application): ViewModel() {
    val repository: CartRepository
    var cartItems: LiveData<List<Cart>>
    var totalPrice = mutableStateOf(0)

    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = CartRepository(cartDao)
        cartItems = repository.getCartList.asLiveData()
    }

    fun deleteAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCartItems()
        }
    }

    fun deleteCheckedItems(menuName:String, option: String, shot: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCheckedCartItems(menuName, option, shot)
        }
    }


    fun addCartItem(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCartItem(item)
        }
    }

    fun updateChecked(check: Boolean, menuName: String, option: String, extraShot: Boolean){
        viewModelScope.launch {
            repository.updateItemChecked(check, menuName, option, extraShot)
        }
    }

    fun initCheckBoxState(){
        viewModelScope.launch{
            cartItems.value?.forEach {
                repository.updateItemChecked(false, it.menuName, it.option, it.shot)
            }
        }
    }

    fun getExtraPrice(item: Cart): Int {
        var extraPrice =
        if(item.option.contains(Sizes.LARGE.text)) Sizes.LARGE.extraPrice
        else if (item.option.contains(Sizes.EXTRA.text)) Sizes.EXTRA.extraPrice
        else 0

        return extraPrice
    }

    fun operateTotalPrice(item: Cart, checked: Boolean){
        if(checked){
            totalPrice.value += (item.price + getExtraPrice(item)) * item.amount
            if(item.shot)
                totalPrice.value += EXTRA_SHOT_PRICE * item.amount
        }

        else{
            totalPrice.value -= (item.price + getExtraPrice(item)) * item.amount
            if(item.shot)
                totalPrice.value -= EXTRA_SHOT_PRICE * item.amount
        }
    }

    fun decreaseTotalPrice(item: Cart){
        if(item.checked){
            totalPrice.value -= (item.price + getExtraPrice(item))
            if(item.shot)
                totalPrice.value -= EXTRA_SHOT_PRICE
        }
    }

    fun increaseTotalPrice(item: Cart){
        if(item.checked){
            totalPrice.value += (item.price + getExtraPrice(item))
            if(item.shot)
                totalPrice.value += EXTRA_SHOT_PRICE
        }
    }

    fun initialTotalPrice(){
        totalPrice.value = 0
    }

}