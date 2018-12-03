package com.example.android.hw6

import android.content.Context
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.android.hw6.adapters.RecyclerViewAdapter
import com.example.android.hw6.models.WeatherForecast
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecast_recycler_view.layoutManager = LinearLayoutManager(this)
        forecast_recycler_view.addItemDecoration(ItemDecorator())
        forecast_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        if (wifiInfo.supplicantState == SupplicantState.UNINITIALIZED) {
            Toast.makeText(this, "No wifi", Toast.LENGTH_LONG).show()
        }
        MyTask().execute()

    }

    inner class MyTask : AsyncTask<Void, Void, String>() {

        var weatherForecast: WeatherForecast? = null
        private val httpClient = OkHttpClient()
        private val gson = Gson()

        override fun doInBackground(vararg params: Void): String {
            val request = Request.Builder()
                    .url("http://api.openweathermap.org/data/2.5/forecast?q=Kiev&mode=json&APPID=5ec0b56c5a95e5a427e11f3fb479e689")
                    .build()
            val response = httpClient.newCall(request).execute()
            return response.body().string()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            weatherForecast = gson.fromJson<WeatherForecast>(result, WeatherForecast::class.java)
            forecast_recycler_view.adapter = RecyclerViewAdapter(this@MainActivity, weatherForecast?.list
                    ?: emptyList())
        }
    }
}
