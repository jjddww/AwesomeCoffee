package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    @SerializedName("menuName") val menuName: String = "",
    @SerializedName("quantity") val quantity: Int = 0,
    @SerializedName("option") val option: String = "",
    @SerializedName("date") val date: Date
)
