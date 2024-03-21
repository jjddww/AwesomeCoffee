package com.jjddww.awesomecoffee.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): ViewModel() {
    val repository: CartRepository
    init {
        val db = AppDatabase.getInstance(application)
        val cartDao = db.cartDao()
        repository = CartRepository(cartDao)
    }

    val cartItems = repository.getCartList.asLiveData()

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
}