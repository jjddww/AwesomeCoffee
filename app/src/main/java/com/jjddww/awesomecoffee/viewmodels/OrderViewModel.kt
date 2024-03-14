package com.jjddww.awesomecoffee.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.model.SubCategory
import com.jjddww.awesomecoffee.data.repository.OrderRepository
import com.jjddww.awesomecoffee.utilities.BEVERAGE
import com.jjddww.awesomecoffee.utilities.DESSERT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {

    var repository = OrderRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var menuData = repository.getMenuList().asLiveData()

    var beverageMenuList = MutableLiveData<List<Menu>>()

    var dessertMenuList = MutableLiveData<List<Menu>>()

    var beverageCategories = repository.getSubCategoryData(BEVERAGE).asLiveData()

    var dessertCategories = repository.getSubCategoryData(DESSERT).asLiveData()

    var beverageListIndex = MutableLiveData(0)

    var dessertListIndex = MutableLiveData(0)

    fun setBeverageMenuList(category: String){
        if(!menuData.value.isNullOrEmpty()){
            val list = menuData.value?.partition{ it.subCategory == category }?.first
            beverageMenuList.value = if (list.isNullOrEmpty()) emptyList() else list
        }
    }

    fun setDessertMenuList(category: String){
        if(!menuData.value.isNullOrEmpty()){
            val list = menuData.value?.partition{ it.subCategory == category }?.first
            dessertMenuList.value = if (list.isNullOrEmpty()) emptyList() else list
        }
    }

    fun changeIndex(category: String, index: Int){
        if (category == BEVERAGE)
            beverageListIndex.value = index
        else
            dessertListIndex.value = index
    }


}