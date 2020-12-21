package com.bignerdranch.android.yelpapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bignerdranch.android.yelpapp.ServiceLocator
import com.bignerdranch.android.yelpapp.data.Weather
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.sharedpreferences.QueryPreferences
import kotlinx.coroutines.launch

class RestauratViewModel(private val app: Application)
    : AndroidViewModel(app) {
    val weather = MutableLiveData<Weather>()
    var yelpRepo = ServiceLocator.yelpResponse
    var weatherpRepo = ServiceLocator.weatherResponse
    val readAll:LiveData<List<YelpRestaurant>> = yelpRepo.readAllData
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    fun searchRestaurant(auth: String, search: String, lat: String,lon:String)
            : LiveData<List<YelpRestaurant>> {
        QueryPreferences.setStoredQuery(app, search)
        val restaurantslist = MutableLiveData<List<YelpRestaurant>>()
       mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        viewModelScope.launch {
            restaurantslist.value = yelpRepo.searchRestaurants(auth, search, lat, lon)
            mutableSearchTerm.value = search

        }

        return restaurantslist
    }
    fun searchWeather(key : String,latlon : String,days:String):LiveData<Weather>{
        viewModelScope.launch {
            weather.value = weatherpRepo.searchtWeather(key,latlon,days)
        }
        return weather
    }
}