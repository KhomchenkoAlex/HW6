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

        forecast_recycler_view.let {
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(ItemDecorator())
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        if (wifiInfo.supplicantState == SupplicantState.UNINITIALIZED) {
            Toast.makeText(this, "No wifi", Toast.LENGTH_LONG).show()
        }
        HttpHandler(Units.IMPERIAL).execute()
    }

    inner class HttpHandler(val units: Units) : AsyncTask<Void, Void, String>() {

        private var weatherForecast: WeatherForecast? = null
        private val httpClient = OkHttpClient()
        private val gson = Gson()

        override fun doInBackground(vararg params: Void): String {
            val request = Request.Builder()
                    .url(Constants.BASE_URL + units.value)
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
