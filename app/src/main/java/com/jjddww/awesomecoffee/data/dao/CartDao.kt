package com.jjddww.awesomecoffee.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getCartList(): Flow<List<Cart>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCartItem(item: Cart)

    @Query("DELETE FROM cart")
    suspend fun deleteAllItems()

    @Query("DELETE FROM cart where id in (:deleteList)")
    suspend fun deleteCheckedItems(deleteList: List<Cart>)

    @Update
    suspend fun updateCartItem(item: Cart)
}