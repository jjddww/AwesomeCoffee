package com.jjddww.awesomecoffee.utilities

import java.text.DecimalFormat

fun ApplyDecimalFormat(price: Int): String{
    val dec = DecimalFormat("#,###")

    return dec.format(price)
}