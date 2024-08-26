package com.example.janitri_mihirbajpai.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.janitri_mihirbajpai.model.ColorData

@Database(entities = [ColorData::class], version = 1, exportSchema = false)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}
