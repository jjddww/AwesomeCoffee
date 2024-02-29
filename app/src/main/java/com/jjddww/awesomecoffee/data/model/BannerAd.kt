package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class BannerAd (
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("img_url")
    val url: String = ""
)