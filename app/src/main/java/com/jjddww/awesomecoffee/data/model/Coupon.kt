package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class Coupon(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("date")
    val date: String = ""
)
