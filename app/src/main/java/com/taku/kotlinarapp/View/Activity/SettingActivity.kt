package com.taku.kotlinarapp.View.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.taku.kotlinarapp.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import com.taku.kotlinarapp.Constans
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.LinearLayout
import butterknife.bindView
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig


class SettingActivity : AppCompatActivity() {

    private val mSettingLayout: LinearLayout by bindView(R.id.setting_layout)
    private val mMarker: Button by bindView(R.id.marker_btn)
    private val mTarget: Button by bindView(R.id.target_btn)
    private var isTarget: Boolean? = null
    private val RESULT_PICK_IMAGEFILE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        tutorial()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                val strDocId:String = DocumentsContract.getDocumentId(resultData.data)
                val strSplittedDocId = strDocId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val strId = strSplittedDocId[strSplittedDocId.size - 1]
                val crsCursor = contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.MediaColumns.DATA),
                        "_id=?",
                        arrayOf(strId),
                        null
                )
                crsCursor!!.moveToFirst()
                val filePath = crsCursor.getString(0)
                setFilePath(filePath)
            } else {
                //TODO エラー処理を書く
            }
        }
    }

    private fun tutorial() {
        val config = ShowcaseConfig()
        config.delay = 500 // half second between each showcase view

        val sequence = MaterialShowcaseSequence(this, Constans.showcaseId.SHOWCASE_ID_SETTING)

        sequence.setConfig(config)

        sequence.addSequenceItem(mMarker,
                getText(R.string.setting_marker_tutorial).toString(), getText(R.string.ok).toString())

        sequence.addSequenceItem(mTarget,
                getText(R.string.setting_target_tutorial).toString(), getText(R.string.ok).toString())

        sequence.start()
    }

    fun settingAction(view: View) {
        val id: Int = view.id
        when (id) {
            R.id.marker_btn -> isTarget = false
            R.id.target_btn -> isTarget = true
        }
        selectPhoto()
    }

    private fun selectPhoto() {
        val intent: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("image/*")
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
    }

    private fun setFilePath(filePath: String) {
        val prefs: SharedPreferences = getSharedPreferences(getText(R.string.setting_ar).toString(), Context.MODE_PRIVATE)
        val message: String
        if (isTarget!!) {
            prefs.edit().putString(Constans.bundleKey.AR_TARGET, filePath).apply()
            message = getText(R.string.complete_set_target).toString()
        } else {
            prefs.edit().putString(Constans.bundleKey.AR_MARKER, filePath).apply()
            message = getText(R.string.complete_set_marker).toString()
        }
        Snackbar.make(mSettingLayout, message, Snackbar.LENGTH_LONG).show()

    }
}
