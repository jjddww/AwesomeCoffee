package com.jjddww.awesomecoffee.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.DetailRepository

class DetailViewModel(var id: Int): ViewModel() {

    var repository = DetailRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    val description: LiveData<List<Menu>> = repository.getMenuDescription(id).asLiveData()
}