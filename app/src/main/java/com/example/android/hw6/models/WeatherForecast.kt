package com.example.android.hw6.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherForecast(
        @SerializedName("cod")
        val statusCode: Int,
        @SerializedName("list")
        val list: List<Forecast>): Serializable {
}