package com.example.janitri_mihirbajpai

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ColorData::class], version = 1, exportSchema = false)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}
