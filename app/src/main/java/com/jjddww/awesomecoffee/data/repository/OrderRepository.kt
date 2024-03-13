package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach

class OrderRepository(private val apiHelper: ApiServiceHelper) {

    fun getMainCategoryData() =
        apiHelper.getMainCategory()
            .catch { e ->
                Log.e("get Main Category Api Error:", e.toString())
            }

    fun getSubCategoryData(category: String) =
        apiHelper.getSubCategory(category)
            .catch {e ->
                Log.e("get Sub Category Api Error", e.toString())
            }

    fun getMenuList(subCategory: String) =
        apiHelper.getMenuList(subCategory)
            .catch {e ->
                Log.e("get Menu List Api Error", e.toString())
            }


}