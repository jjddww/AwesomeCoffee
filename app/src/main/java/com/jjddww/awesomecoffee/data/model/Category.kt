package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class MainCategory(
    @SerializedName("main_category")
    var category: String = ""
)

data class SubCategory(
    @SerializedName("main_category")
    var mainCategory: String = "",
    @SerializedName("sub_category")
    var subCategory: String = ""
)
