package com.example.android.hw6.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Main(
        //@SerializedName("temp")
        val temp: Double,
        @SerializedName("temp_min")
        val minTemp: Double,
        @SerializedName("temp_max")
        val maxTemp: Double,
        //@SerializedName("humidity")
        val humidity: Int
): Serializable {
}