package com.example.android.hw6.models

import java.io.Serializable

data class Weather(
        //@SerializedName("main")
        val main: String,
       // @SerializedName("description")
        val description: String
//        @SerializedName("icon")
//        val icon: String
): Serializable {
}