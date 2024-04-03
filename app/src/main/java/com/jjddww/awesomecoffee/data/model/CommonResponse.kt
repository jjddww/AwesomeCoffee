package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("data")
    val data: String = ""
)
