package com.example.janitri_mihirbajpai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.janitri_mihirbajpai.model.ColorData
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Insert
    suspend fun insert(colorData: ColorData)

    @Query("SELECT * FROM color_table ORDER BY id DESC")
    fun getAllColors(): Flow<List<ColorData>>
}
