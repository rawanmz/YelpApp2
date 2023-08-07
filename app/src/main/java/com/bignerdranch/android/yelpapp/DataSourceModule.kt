package com.bignerdranch.android.yelpapp

import com.bignerdranch.android.yelpapp.repository.WeatherDataSource
import com.bignerdranch.android.yelpapp.repository.WeatherDataSourceImp
import com.bignerdranch.android.yelpapp.repository.YelpDataSource
import com.bignerdranch.android.yelpapp.repository.YelpDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindWeatherDataSource(impl: WeatherDataSourceImp): WeatherDataSource

    @Binds
    abstract fun bindYelpDataSource(impl: YelpDataSourceImp): YelpDataSource

}