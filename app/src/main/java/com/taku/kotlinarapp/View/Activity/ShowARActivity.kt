package com.taku.kotlinarapp.View.Activity

import android.os.Bundle
import com.taku.kotlinarapp.R

import eu.kudan.kudan.ARAPIKey
import eu.kudan.kudan.ARActivity

class ShowARActivity : ARActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_ar)
        val apiKey: ARAPIKey = ARAPIKey.getInstance()
        apiKey.setAPIKey(getText(R.string.ar_key).toString())
    }
}
