package com.example.janitri_mihirbajpai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_table")
data class ColorData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hex: String,
    val date: String
)