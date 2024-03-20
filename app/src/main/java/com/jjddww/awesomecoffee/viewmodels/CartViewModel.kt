package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository): ViewModel() {

    val cartItems = repository.getCartList.asLiveData()

    fun deleteAllItems() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllCartItems()
    }

    fun deleteCheckedItems(items: List<Cart>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCheckedCartItems(items)
    }

    fun updateItem(item: Cart) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateCartItem(item)
    }
}