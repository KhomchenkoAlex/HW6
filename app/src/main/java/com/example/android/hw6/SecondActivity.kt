package com.example.android.hw6

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.hw6.models.Forecast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val forecast = intent.getSerializableExtra("itemForecast") as Forecast
        println(forecast)

        date_view.text = forecast.date
        max_temp_value.text = forecast.main.maxTemp.toString()
        min_temp_value.text = forecast.main.minTemp.toString()
        humidity.text = forecast.main.humidity.toString()
        description.text = forecast.weather.first().description

    }
}
