package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Order
import com.jjddww.awesomecoffee.data.repository.OrderHistoryRepository
import kotlinx.coroutines.launch

class OrderHistoryViewModel: ViewModel() {

    private val repository = OrderHistoryRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var orderHistory = getHistory()

    private fun getHistory(): LiveData<List<Order>> {
        var history = MutableLiveData<List<Order>>()

        viewModelScope.launch {
            history = repository.getOrderHistory().asLiveData() as MutableLiveData<List<Order>>
        }

        return history
    }
}