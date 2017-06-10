package com.taku.kotlinarapp.View.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.taku.kotlinarapp.R
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.widget.LinearLayout
import butterknife.bindView
import com.taku.kotlinarapp.Constans
import eu.kudan.kudan.ARAPIKey


class MainActivity : AppCompatActivity() {

    private val mainLayout: LinearLayout by bindView(R.id.main_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiKey: ARAPIKey = ARAPIKey.getInstance()
        apiKey.setAPIKey(getText(R.string.ar_key).toString())
    }

    fun moveActivity(view: View) {
        val id: Int = view.id
        val empty: String = "empty"
        var intent: Intent? = null
        val prefs: SharedPreferences = getSharedPreferences(getText(R.string.setting_ar).toString(), Context.MODE_PRIVATE)
        when (id) {
            R.id.show_ar -> {
                intent = Intent(this, ShowARActivity::class.java)
                val markerPath: String = prefs.getString(Constans.bundleKey.AR_MARKER, empty)
                val targetPath: String = prefs.getString(Constans.bundleKey.AR_TARGET, empty)
                if (markerPath != empty && targetPath != empty) {
                    intent.putExtra(Constans.bundleKey.AR_TARGET, targetPath)
                    intent.putExtra(Constans.bundleKey.AR_MARKER, markerPath)
                    startActivity(intent)
                } else {
                    Snackbar.make(mainLayout, getText(R.string.alert), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }
            }

            R.id.setting -> {
                intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

