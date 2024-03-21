package com.jjddww.awesomecoffee.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.ui.platform.LocalContext
import com.jjddww.awesomecoffee.data.AppDatabase
import com.jjddww.awesomecoffee.data.api.ApiServiceHelper
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.data.model.Menu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class DetailRepository (private val apiHelper: ApiServiceHelper,
    private val cartDao: CartDao) {
    fun getMenuDescription(id: Int): Flow<List<Menu>> =
        apiHelper.getMenuDescription(id)
            .catch { e ->
                Log.e("get menu desc Api Error", e.toString())
            }

    @WorkerThread
    suspend fun addCartItem(item: Cart) {
        cartDao.addCartItem(item)
    }

    fun findSameItem(menuName: String, option: String, extraShot: Boolean): Flow<Int> =
        cartDao.findSameMenuAmount(menuName, option, extraShot)

}