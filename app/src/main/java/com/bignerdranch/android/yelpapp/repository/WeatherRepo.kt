package com.bignerdranch.android.yelpapp.repository

import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.data.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepo @Inject constructor(private val remoteSource: WeatherDataSource,
):WeatherDataSource{

    override suspend fun searchWeather(key: String, q : String, days : String): Weather {
        val weather=remoteSource.searchWeather(key,q,days)
        return weather

    }
}

