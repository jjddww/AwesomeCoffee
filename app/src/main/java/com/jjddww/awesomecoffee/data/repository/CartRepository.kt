package com.jjddww.awesomecoffee.data.repository

import androidx.annotation.WorkerThread
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn

class CartRepository (private val cartDao: CartDao){

    val getCartList: Flow<List<Cart>> = cartDao.getCartList()

    @WorkerThread
    suspend fun updateItemChecked(check: Boolean, menuName: String, option: String, extraShot: Boolean){
        cartDao.updateChecked(check, menuName, option, extraShot)
    }


    @WorkerThread
    suspend fun deleteAllCartItems(){
        cartDao.deleteAllItems()
    }

    @WorkerThread
    suspend fun deleteCheckedCartItems(menuName: String, option: String, extraShot: Boolean){
        cartDao.deleteCheckedItems(menuName, option, extraShot)
    }

    @WorkerThread
    suspend fun addCartItem(item: Cart) {
        cartDao.addCartItem(item)
    }
}