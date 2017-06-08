package com.taku.kotlinarapp

import android.os.Bundle
import eu.kudan.kudan.ARAPIKey
import eu.kudan.kudan.ARActivity

class MainActivity : ARActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiKey:ARAPIKey = ARAPIKey.getInstance()
        apiKey.setAPIKey(getText(R.string.ar_key).toString())
    }
}

