package com.bignerdranch.android.yelpapp.data

import com.bignerdranch.android.yelpapp.data.YelpRestaurant
import com.google.gson.annotations.SerializedName

class YelpBusinessesResponse {
    @SerializedName("businesses")
    lateinit var restaurants: List<YelpRestaurant>
}