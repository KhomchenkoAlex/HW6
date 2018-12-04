package com.example.android.hw6

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.hw6.utils.Units
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        radio_group.setOnCheckedChangeListener { _, checkedId -> setUnitsValue(checkedId) }

        current_temp_value.text = intent.getDoubleExtra("current_temp", 0.0).toString()
        val currentUnitsCode = intent.getIntExtra("current_units", 0)
        when (currentUnitsCode) {
            1 -> celsius_button.isChecked = true
            2 -> fahrenheit_button.isChecked = true
            else -> kelvin_button.isChecked = true
        }
    }

    private fun setUnitsValue(id: Int) {
        when (id) {
            celsius_button.id -> setSharedPreferences(Units.METRIC)
            fahrenheit_button.id -> setSharedPreferences(Units.IMPERIAL)
            kelvin_button.id -> setSharedPreferences(Units.DEFAULT)
        }
    }

    private fun setSharedPreferences(units: Units) {
        val sharedPreferences = getSharedPreferences("Units", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("units", units.code).apply()
    }
}
