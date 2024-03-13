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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {

    var repository = OrderRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var menuData: MutableLiveData<List<Menu>> = MutableLiveData()
    var menuList = MediatorLiveData<List<Menu>>()

    fun ddd():  LiveData<List<SubCategory>>{
        return repository.getSubCategoryData("ㅇ").asLiveData()
    }


    fun getSubCategory(category: String): LiveData<List<SubCategory>> =
        repository.getSubCategoryData(category).asLiveData()

    fun getMenuLivedata(subCategory: String): LiveData<List<Menu>> {
        return repository.getMenuList(subCategory).asLiveData()
    }


    fun getMenuList(subCategory: String){
        menuList.addSource(getMenuLivedata(subCategory)) { menuData.value = it }
    }



}