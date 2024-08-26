package com.example.janitri_mihirbajpai.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.janitri_mihirbajpai.model.ColorData
import com.example.janitri_mihirbajpai.data.ColorDao
import com.example.janitri_mihirbajpai.model.CloudColorData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    fun fetchColorsFromCloud(): LiveData<List<CloudColorData>> {
        val colorsLiveData = MutableLiveData<List<CloudColorData>>()

        colorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val colorList = mutableListOf<CloudColorData>()
                for (snapshot in dataSnapshot.children) {
                    val colorData = snapshot.getValue(CloudColorData::class.java)
                    colorData?.let { colorList.add(it) }
                }
                colorsLiveData.value = colorList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ColorRepository", "fetchColorsFromCloud() failed: ${databaseError.message}")
                colorsLiveData.value = emptyList()
            }
        })

        return colorsLiveData
    }


}
