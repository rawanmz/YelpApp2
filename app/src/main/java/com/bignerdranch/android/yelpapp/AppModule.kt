package com.bignerdranch.android.yelpapp

import android.content.Context
import com.bignerdranch.android.yelpapp.api.WeatherApi
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.database.YelpDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun initializeNetworkYelp(): YelpApi {
        return Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
            .build()
            .create(YelpApi::class.java)
    }


    @Singleton
    @Provides
    fun initializeNetworkWeather(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()
            .create(WeatherApi::class.java)
    }
    @Singleton
    @Provides
    fun provideYelpDao(db: YelpDatabase) = db.dao()

    @Singleton
    @Provides
    fun provideDayPlanDao(db: YelpDatabase) = db.dayPlanDao()

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        YelpDatabase.getInstance(context)
//    @Singleton
//    @Provides
//    fun provideYelpRetrofit(
//        @AppAPI retrofit: Retrofit
//    )=retrofit.create(
//        YelpApi::class.java
//    )
//    @Singleton
//    @Provides
//    fun provideWeatherRetrofit(
//        @AppAPI retrofit: Retrofit
//    )=retrofit.create(
//        WeatherApi::class.java
//    )
}