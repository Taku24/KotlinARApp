package com.taku.kotlinarapp.View.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.taku.kotlinarapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun moveActivity(view: View) {
        val id: Int = view.id
        var intent: Intent? = null
        when (id) {
            R.id.show_ar -> intent = Intent(this, ShowARActivity::class.java)
            R.id.setting -> intent = Intent(this, SettingActivity::class.java)
        }
        startActivity(intent)
    }
}

