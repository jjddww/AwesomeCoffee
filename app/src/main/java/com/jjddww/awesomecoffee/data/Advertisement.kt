package com.jjddww.awesomecoffee.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advertisement")
data class Advertisement(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name= "url") val url: String
)
