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
import java.io.File
import android.provider.DocumentsContract




class SettingActivity : AppCompatActivity() {

    private var isTarget: Boolean? = null
    private val RESULT_PICK_IMAGEFILE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                val strDocId = DocumentsContract.getDocumentId(resultData.data)
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
        if (isTarget!!) {
            prefs.edit().putString(Constans.bundleKey.AR_TARGET, filePath).apply()
        } else {
            prefs.edit().putString(Constans.bundleKey.AR_MARKER, filePath).apply()
        }
    }
}
