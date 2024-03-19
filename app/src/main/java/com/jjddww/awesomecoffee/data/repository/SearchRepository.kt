package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.flow.catch

class SearchRepository (private val apiHelper: ApiServiceHelper){

    fun getSearchResult(keyword: String) =
        apiHelper.getSearchResult(keyword)
            .catch { e ->
                Log.e("get Menu Search Result Api Error", e.toString())
            }
}