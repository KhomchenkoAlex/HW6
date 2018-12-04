package com.example.android.hw6

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.hw6.models.Forecast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private var degreeSuffix = "K"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val forecast = intent.getSerializableExtra("itemForecast") as Forecast
        val sharedPreferences = getSharedPreferences("Units", Context.MODE_PRIVATE).getInt("units", 0)
        when(sharedPreferences){
            1-> degreeSuffix = "C"
            2-> degreeSuffix = "F"
        }

        date_view.text = forecast.date
        max_temp_value.text = forecast.main.maxTemp.toString() + degreeSuffix
        min_temp_value.text = forecast.main.minTemp.toString()+ degreeSuffix
        humidity.text = forecast.main.humidity.toString()+ "%"
        description.text = forecast.weather.first().description
        wind_value.text = forecast.wind.speed.toString()
    }
}
