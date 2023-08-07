package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.YelpBusinessesResponse
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import javax.inject.Inject

interface YelpDataSource {
//    val readAllData: LiveData<List<YelpRestaurant>>
    suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon:String
    ): Result<YelpBusinessesResponse>
//    suspend fun searchRestaurantById(key:String): YelpRestaurant
//    val readAllDayPlan : LiveData<List<DayPlan>>
//    suspend fun addDayPlan(plan: DayPlan)
//    suspend fun deleteDayPlan(plan: DayPlan)
//    suspend fun updateDayPlan(plan: DayPlan)
//    suspend fun searchPlanById(key:String):DayPlan
}
class YelpDataSourceImp @Inject constructor(
    private val service: YelpApi
):YelpDataSource {

    override suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon: String
    ): Result<YelpBusinessesResponse> = service.searchRestaurants(auth,term,lat,lon)
    }
