package com.jjddww.awesomecoffee.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jjddww.awesomecoffee.data.model.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart ORDER BY menu_name DESC") //장바구니 목록 불러올 때
    fun getCartList(): Flow<List<Cart>>

    @Query("SELECT * FROM cart WHERE checked = true")
    fun findCheckedItem(): Flow<List<Cart>>

    @Query("UPDATE cart SET checked  = :check WHERE menu_name = :menuName AND option = :option AND extra_shot = :extraShot ")
    suspend fun updateChecked(check: Boolean, menuName: String, option: String, extraShot: Boolean)

    @Query("SELECT amount FROM cart WHERE menu_name = :menuName AND option = :option AND extra_shot = :extraShot")
    fun findSameMenuAmount(menuName: String, option: String, extraShot: Boolean):Flow<Int>

    @Query("DELETE from cart") //장바구니 모두 지우기
    suspend fun deleteAllItems()

    @Query("DELETE FROM cart WHERE menu_name = :menuName AND option = :option AND extra_shot = :extraShot") //체크한 아이템 삭제
    suspend fun deleteCheckedItems(menuName: String, option: String, extraShot: Boolean)

    @Query("DELETE FROM cart WHERE checked = true")
    suspend fun deleteCheckedCartItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartItem(item: Cart)

}