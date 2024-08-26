package com.example.janitri_mihirbajpai.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.janitri_mihirbajpai.model.CloudColorData
import com.example.janitri_mihirbajpai.model.ColorData
import com.example.janitri_mihirbajpai.repository.ColorRepository
import com.example.janitri_mihirbajpai.utils.Utils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {

    val allColors: LiveData<List<ColorData>> = repository.allColors.asLiveData()

    fun addRandomColor() {
        val currentDate = SimpleDateFormat("MM/dd/yyyy").format(Date())
        val newColor = ColorData(hex = Utils.generateRandomColorHex(), date = currentDate)
        viewModelScope.launch {
            repository.insert(newColor)
        }
    }

    fun storeColors(colorsList: List<ColorData>): LiveData<Boolean> {
        return repository.storeToCloud(colorsList)
    }

    val fetchedColors: LiveData<List<CloudColorData>> = repository.fetchColorsFromCloud()
}

