package com.example.android.hw6

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.hw6.adapters.RecyclerViewAdapter
import com.example.android.hw6.decorators.ItemDecorator
import com.example.android.hw6.models.WeatherForecast
import com.example.android.hw6.utils.Constants
import com.example.android.hw6.utils.Units
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentTemp: Double? = null
    var units = Units.DEFAULT

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
        if (wifiInfo.networkId == -1) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("Units", Context.MODE_PRIVATE).getInt("units", 0)
        when(sharedPreferences){
            1-> units = Units.METRIC
            2-> units = Units.IMPERIAL
        }
        HttpHandler(units).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.units_settings -> {
                val settingsIntent = Intent(this, ThirdActivity::class.java)
                settingsIntent.putExtra("current_temp", currentTemp).putExtra("current_units", units.code)
                startActivity(settingsIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
            currentTemp = weatherForecast?.list?.first()?.main?.temp ?: 0.0
            forecast_recycler_view.adapter = RecyclerViewAdapter(this@MainActivity, weatherForecast?.list
                    ?: emptyList())
        }
    }
}
