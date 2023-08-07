package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.data.Weather
import javax.inject.Inject

interface WeatherDataSource {

    suspend fun searchWeather(key: String, q: String, days: String): Weather
}
class WeatherDataSourceImp @Inject constructor(
    private val weatherApi:WeatherApi
):WeatherDataSource {
    override suspend fun searchWeather(key: String, q: String, days: String): Weather {
        return weatherApi.searchForecastWeather(key,q,days)
    }
}