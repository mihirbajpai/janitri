package com.example.janitri_mihirbajpai

import androidx.lifecycle.*
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
}

