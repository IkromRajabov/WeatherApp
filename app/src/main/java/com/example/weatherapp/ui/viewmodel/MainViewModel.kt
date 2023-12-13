package com.example.weatherapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repositories.WeatherRepository
import com.example.weatherapp.domain.model.CurrentWeatherCardModel
import com.example.weatherapp.domain.model.DaysWeatherItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {
    private val cityName = "Kirov-Chepetsk"
    private val _currentWeatherCard = MutableLiveData<CurrentWeatherCardModel>()
    val currentWeather: LiveData<CurrentWeatherCardModel>
        get() = _currentWeatherCard

    private val _daysList = MutableLiveData<List<DaysWeatherItemModel>>()
    val daysList: LiveData<List<DaysWeatherItemModel>>
        get() = _daysList

    fun getCurrentWeatherCard() {
        viewModelScope.launch {
            try {
                val weatherData = repository.getCurrentWeatherCard(cityName)
                weatherData?.let {
                    _currentWeatherCard.value = it
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("LOGGGG: ", it) }
            }
        }
    }

    fun updateDaysList() {
        viewModelScope.launch {
            try {
                val daysListData = repository.getDaysItemWeather(cityName)
                Log.e("LOGGGG: ", daysListData.toString())
                daysListData?.let {
                    _daysList.value = it
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("LOGGGG: ", it) }
            }
        }
    }
}
