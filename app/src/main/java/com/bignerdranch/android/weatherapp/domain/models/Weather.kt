package com.bignerdranch.android.weatherapp.domain.models

import com.google.gson.annotations.SerializedName

class Weather(
   val location: Location,

   @SerializedName("current")
    val currentConditions: CurrentConditions
)

