package com.example.android.hw6.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forecast(
        @SerializedName("dt_txt")
        val date: String,
        //@SerializedName("main")
        val main: Main,
        val weather: List<Weather>,
        //@SerializedName("wind")
        val wind: Wind) : Serializable {
}