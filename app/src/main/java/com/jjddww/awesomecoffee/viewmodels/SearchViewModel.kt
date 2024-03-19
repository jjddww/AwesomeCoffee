package com.jjddww.awesomecoffee.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jjddww.awesomecoffee.data.api.ApiServiceHelperImpl
import com.jjddww.awesomecoffee.data.api.RetrofitClient
import com.jjddww.awesomecoffee.data.model.Menu
import com.jjddww.awesomecoffee.data.repository.SearchRepository

class SearchViewModel: ViewModel() {

    private val repository = SearchRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))

    var text = mutableStateOf("")

    var result: MutableLiveData<List<Menu>> = repository.getSearchResult(text.value).asLiveData() as MutableLiveData<List<Menu>>

    fun menuSearch(keyword:String) {
        result = repository.getSearchResult(keyword).asLiveData() as MutableLiveData<List<Menu>>
    }
}