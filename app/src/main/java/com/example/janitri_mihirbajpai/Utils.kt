package com.example.janitri_mihirbajpai

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.random.Random

object Utils{
    fun generateRandomColorHex(): String {
        val random = Random(System.currentTimeMillis())
        val color = Color(
            red = random.nextInt(256),
            green = random.nextInt(256),
            blue = random.nextInt(256)
        )
        return String.format("#%06X", color.toArgb() and 0xFFFFFF)
    }
}