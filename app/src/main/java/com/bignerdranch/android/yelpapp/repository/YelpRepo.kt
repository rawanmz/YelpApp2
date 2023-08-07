package com.bignerdranch.android.yelpapp.repository

import androidx.lifecycle.LiveData
import com.bignerdranch.android.yelpapp.api.YelpApi
import com.bignerdranch.android.yelpapp.data.YelpBusinessesResponse
import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.bignerdranch.android.yelpapp.database.DayPlan
import com.bignerdranch.android.yelpapp.database.DayPlanDao
import com.bignerdranch.android.yelpapp.database.YelpDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YelpRepo @Inject constructor(
    // private val yelpApi: YelpApi,
     private val resDao: YelpDao,
     private val dayPlanDao: DayPlanDao,
     private val remoteSource: YelpDataSource){

     val readAllData: LiveData<List<YelpRestaurant>> = resDao.readAllData()

     suspend fun searchRestaurants(
        auth: String,
        term: String,
        lat: String,
        lon: String
    ):Result <YelpBusinessesResponse> {
        resDao.deleteRestaurant()
        val restaurant = remoteSource.searchRestaurants(auth, term, lat, lon).onSuccess {
            resDao.addData(
                *it.restaurants.map {
                    YelpRestaurant(
                        it.yelpId,
                        it.name,
                        it.rating,
                        it.phone,
                        it.is_closed,
                        it.numReviews,
                        it.distanceInMeters,
                        it.imageUrl,
                        it.categories,
                        it.coordinates,
                        it.listDescription
                    )
                }.toTypedArray()
            )
        }
        return restaurant
    }

     suspend fun searchRestaurantById(key: String): YelpRestaurant {
        return resDao.searchResturantById(key)
    }

     val readAllDayPlan: LiveData<List<DayPlan>> = dayPlanDao.readDayPlanAllData()

     suspend fun addDayPlan(plan: DayPlan) {
        dayPlanDao.addDayPlanData(plan)
    }

     suspend fun deleteDayPlan(plan: DayPlan) {
        dayPlanDao.deletePlan(plan)
    }

     suspend fun updateDayPlan(plan: DayPlan) {
        dayPlanDao.updateData(plan)
    }

     suspend fun searchPlanById(key: String): DayPlan {
        return dayPlanDao.searchPlanById(key)
    }
}
