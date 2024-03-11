package com.jjddww.awesomecoffee.data.model

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("menu_name")
    var menuName: String = "",
    @SerializedName("img_url")
    var imgUrl: String = "",
    @SerializedName("type")
    var type: String = "d",
    @SerializedName("main_category")
    var mainCategory: String = "",
    @SerializedName("sub_category")
    var subCategory: String = "",
    @SerializedName("english_name")
    var englishMenuName: String = "",
    @SerializedName("price")
    var price: Int = 0,
    @SerializedName("description")
    var desc: String = "",
    @SerializedName("temperature")
    var temperature: String = "none")
