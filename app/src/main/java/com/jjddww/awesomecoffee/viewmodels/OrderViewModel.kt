package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.OrderRepository
import com.jjddww.awesomecoffee.utilities.BEVERAGE

class OrderViewModel: ViewModel() {

    private val repository = OrderRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var menuData = repository.getMenuList().asLiveData()

    var filteredList1 = MutableLiveData<List<Menu>>()
    var filteredList2 = MutableLiveData<List<Menu>>()


    fun setBeverageMenuList(main: String, category: String){
        if(!menuData.value.isNullOrEmpty()){
            val list = menuData.value?.partition{ it.subCategory == category }?.first

            if(main == BEVERAGE)
                filteredList1.value = if (list.isNullOrEmpty()) emptyList() else list
            else
                filteredList2.value = if (list.isNullOrEmpty()) emptyList() else list
        }
    }


}