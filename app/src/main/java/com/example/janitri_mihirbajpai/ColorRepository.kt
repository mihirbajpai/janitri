package com.example.janitri_mihirbajpai

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlinx.coroutines.flow.Flow

class ColorRepository(private val colorDao: ColorDao) {

    val allColors: Flow<List<ColorData>> = colorDao.getAllColors()


    private val database: FirebaseDatabase = Firebase.database
    private val colorsRef = database.getReference("colors")

    suspend fun insert(colorData: ColorData) {
        colorDao.insert(colorData)
    }

    fun storeToCloud(colorsList: List<ColorData>): LiveData<Boolean> {
        val successLiveData = MutableLiveData<Boolean>()

        colorsRef.setValue(colorsList)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    successLiveData.postValue(true)
                } else {
                    successLiveData.postValue(false)
                }
            }.addOnFailureListener {
                Log.e("ColorRepository", "storeToCloud() == ${it.message}")
            }

        return successLiveData
    }

}
