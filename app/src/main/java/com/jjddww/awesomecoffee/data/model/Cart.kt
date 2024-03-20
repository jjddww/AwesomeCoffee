package com.jjddww.awesomecoffee.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name= "url") val url: String,
    @ColumnInfo(name= "menu_name") val menuName: String,
    @ColumnInfo(name= "price") val price: Int,
    @ColumnInfo(name= "option") val option: String,
    @ColumnInfo(name= "extra_shot") val shot: String,
    @ColumnInfo(name= "amount") val amount: String
)
