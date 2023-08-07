package com.bignerdranch.android.yelpapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.repository.WeatherRepo
import com.bignerdranch.android.yelpapp.repository.YelpRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
        var yelpRepo: YelpRepo,
        var weatherRepo:WeatherRepo
) : ViewModel() {

    var weather = MutableLiveData<Weather>()
    val readAll: LiveData<List<YelpRestaurant>> = yelpRepo.readAllData
    val restaurantsList = MutableLiveData<List<YelpRestaurant>>()
    val restaurantItem = MutableLiveData<YelpRestaurant>()
    val dayPlan = MutableLiveData<DayPlan>()


    fun searchRestaurant(auth: String, search: String, lat: String, lon: String)
            : LiveData<List<YelpRestaurant>> {
        viewModelScope.launch {
            val result= yelpRepo.searchRestaurants(auth, search, lat, lon)
            restaurantsList.postValue(result.getOrNull()?.restaurants)
        }
        return restaurantsList
    }

    fun searchForecastWeather(key: String, latlon: String, days: String): LiveData<Weather> {
        viewModelScope.launch {
            weather.value = weatherRepo.searchWeather(key, latlon, days)
        }
        return weather
    }

    fun searchRestaurantById(id: String): MutableLiveData<YelpRestaurant> {
        viewModelScope.launch {
            restaurantItem.value = yelpRepo.searchRestaurantById(id)
        }
        return restaurantItem
    }

    val planDays: LiveData<List<DayPlan>> = yelpRepo.readAllDayPlan

    fun addDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.addDayPlan(plan)
        }
    }

    fun deleteDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.deleteDayPlan(plan)
        }
    }

    fun updateDayPlan(plan: DayPlan) {
        viewModelScope.launch {
            yelpRepo.updateDayPlan(plan)
        }
    }

    fun searchDayPlan(planId: String): MutableLiveData<DayPlan> {
        viewModelScope.launch {
            dayPlan.value = yelpRepo.searchPlanById(planId)
        }
        return dayPlan
    }
}