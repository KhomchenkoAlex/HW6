package com.example.android.hw6.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.hw6.R
import com.example.android.hw6.SecondActivity
import com.example.android.hw6.models.Forecast

class RecyclerViewAdapter(
        private val context: Context,
        private val items: List<Forecast>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val itemDate: TextView = itemView.findViewById(R.id.date)
        private val itemTemp: TextView = itemView.findViewById(R.id.temperature)
        private val itemWeather: TextView = itemView.findViewById(R.id.weather)

        var itemForecast: Forecast? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(itemView: View) {
            val intent = Intent(context ,SecondActivity::class.java)
            intent.putExtra("itemForecast", itemForecast)
            context.startActivity(intent) }

        fun onBind(item: Forecast) {
            itemForecast = item
            itemDate.text = item.date
            itemTemp.text = item.main.temp.toString()
            itemWeather.text = item.weather.first().main
        }
    }
}