package com.example.android.hw6

enum class Units(val value: String?, val code: Int) {
    METRIC(Constants.metricUnits,1), IMPERIAL(Constants.imperialUnits, 2), DEFAULT("", 0)
}