package com.jjddww.awesomecoffee.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jjddww.awesomecoffee.R

// Set of Material typography styles to start with

val spoqahansansneo = FontFamily(
    Font(R.font.spoqahansansneo_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.spoqahansansneo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.spoqahansansneo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.spoqahansansneo_light, FontWeight.Light, FontStyle.Normal)
)

val notosans = FontFamily(
    Font(R.font.notosans_bold, FontWeight.Bold, FontStyle.Normal)
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = spoqahansansneo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    titleSmall = TextStyle(
        fontFamily = spoqahansansneo,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    labelMedium = TextStyle(
        fontFamily = spoqahansansneo,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    labelSmall = TextStyle(
        fontFamily = spoqahansansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = spoqahansansneo,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)