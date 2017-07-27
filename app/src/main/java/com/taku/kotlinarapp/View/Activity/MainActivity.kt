package com.taku.kotlinarapp.View.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.taku.kotlinarapp.R
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.LinearLayout
import butterknife.bindView
import com.taku.kotlinarapp.Constans
import eu.kudan.kudan.ARAPIKey
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

class MainActivity : AppCompatActivity() {

    private val mainLayout: LinearLayout by bindView(R.id.main_layout)
    private val mShowAR: Button by bindView(R.id.show_ar)
    private val mSetting: Button by bindView(R.id.setting)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiKey: ARAPIKey = ARAPIKey.getInstance()
        apiKey.setAPIKey(getText(R.string.ar_key).toString())
        initPreferences()
        tutorial()
    }

    private fun tutorial() {
        val config: ShowcaseConfig = ShowcaseConfig()
        val sequence: MaterialShowcaseSequence = MaterialShowcaseSequence(this, Constans.showcaseId.SHOWCASE_ID_MAIN)
        config.delay = 500

        sequence.setConfig(config)

        sequence.addSequenceItem(mShowAR, getText(R.string.start_ar).toString(), getText(R.string.ok).toString())
        sequence.addSequenceItem(mSetting, getText(R.string.start_setting).toString(), getText(R.string.ok_setting).toString())
        sequence.setOnItemDismissedListener { materialShowcaseView, i ->
            if(i == 1){
                moveActivity(mSetting)
            }
        }

        sequence.start()
    }

    private fun initPreferences() {
        val editor: SharedPreferences = getSharedPreferences(getText(R.string.setting_ar).toString(), Context.MODE_PRIVATE)
        editor.edit().clear().commit()
    }

    fun moveActivity(view: View) {
        val id: Int = view.id
        val empty: String = getText(R.string.empty).toString()
        var intent: Intent? = null
        val prefs: SharedPreferences = getSharedPreferences(getText(R.string.setting_ar).toString(), Context.MODE_PRIVATE)
        when (id) {
            R.id.show_ar -> {
                val markerPath: String = prefs.getString(Constans.bundleKey.AR_MARKER, empty)
                val targetPath: String = prefs.getString(Constans.bundleKey.AR_TARGET, empty)
                if (markerPath != empty && targetPath != empty) {
                    intent = Intent(this, ShowARActivity::class.java)
                    intent.putExtra(Constans.bundleKey.AR_TARGET, targetPath)
                    intent.putExtra(Constans.bundleKey.AR_MARKER, markerPath)
                    startActivity(intent)
                } else {
                    Snackbar.make(mainLayout, getText(R.string.alert), Snackbar.LENGTH_LONG).show()
                }
            }

            R.id.setting -> {
                intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

