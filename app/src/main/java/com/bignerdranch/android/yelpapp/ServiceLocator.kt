package com.bignerdranch.android.yelpapp

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.database.YelpDatabase
import com.bignerdranch.android.yelpapp.repository.WeatherRepo
import com.bignerdranch.android.yelpapp.repository.YelpRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
//object ServiceLocator {
//    private lateinit var app: App
//
//    fun init(app: App) {
//        this.app = app
////        initializeDatabase(app)
////        initializeNetworkYelp(app)
////        initializeNetworkWeather(app)
//    }
//
//
////    private fun initializeDatabase(
////        context: Context
////    ) = Room.databaseBuilder(context, YelpDatabase::class.java, "db")
////        .fallbackToDestructiveMigration()
////        .build()
//}
