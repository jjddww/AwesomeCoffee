package com.jjddww.awesomecoffee.viewmodels

import android.util.Log
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

class SearchViewModel(): ViewModel() {

    private val repository = SearchRepository(ApiServiceHelperImpl(RetrofitClient.retrofit))


    var text = mutableStateOf("")

    var result = repository.getSearchResult(text.value).asLiveData()

    fun menuSearch(keyword:String) {
        text.value = keyword
        result = repository.getSearchResult(text.value).asLiveData()
    }

    fun clear(){
        text.value = ""
        result = repository.getSearchResult(text.value).asLiveData()
    }
}