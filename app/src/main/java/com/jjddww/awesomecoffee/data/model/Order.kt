package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    @SerializedName("menu_name") val menuName: String = "",
    @SerializedName("quantity") val quantity: Int = 0,
    @SerializedName("p_option") val option: String = "",
    @SerializedName("date") val date: String
)
