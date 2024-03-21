package com.jjddww.awesomecoffee.data.repository

import androidx.annotation.WorkerThread
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow

class CartRepository (private val cartDao: CartDao){

    val getCartList: Flow<List<Cart>> = cartDao.getCartList()

    @WorkerThread
    suspend fun deleteAllCartItems(){
        cartDao.deleteAllItems()
    }

    @WorkerThread
    suspend fun deleteCheckedCartItems(menuName: String, option: String, extraShot: Boolean){
        cartDao.deleteCheckedItems(menuName, option, extraShot)
    }
}