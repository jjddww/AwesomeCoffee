package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class Coupon(
    @SerializedName("coupon_id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("type") //금액 차감 or 퍼센트 할인
    val type: String = "",
    @SerializedName("amount") //차감 금액 or 할인율
    val discount: Int = 0
)
