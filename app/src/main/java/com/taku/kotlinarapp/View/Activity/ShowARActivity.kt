package com.taku.kotlinarapp.View.Activity

import android.app.AlertDialog
import android.os.Bundle
import com.taku.kotlinarapp.Constans
import com.taku.kotlinarapp.R

import eu.kudan.kudan.ARAPIKey
import eu.kudan.kudan.ARActivity
import eu.kudan.kudan.ARImageNode
import eu.kudan.kudan.ARImageTrackable
import eu.kudan.kudan.ARImageTracker
import eu.kudan.kudan.ARTextureMaterial


class ShowARActivity : ARActivity() {

    private var mFirstImageNode: ARImageNode? = null
    private var mTrackable: ARImageTrackable? = null

    private var mMarker: String = ""
    private var mTarget: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_ar)
        if (intent.hasExtra(Constans.bundleKey.AR_TARGET) && intent.hasExtra(Constans.bundleKey.AR_MARKER)) {
            mMarker = intent.getStringExtra(Constans.bundleKey.AR_MARKER)
            mTarget = intent.getStringExtra(Constans.bundleKey.AR_TARGET)
            setUpAR()
        } else {
            AlertDialog.Builder(this)
                    .setTitle(getText(R.string.error))
                    .setMessage(getText(R.string.error_content))
                    .setPositiveButton("OK", { _, _ ->
                        finish()
                    })
                    .show()
        }
    }

    private fun setUpAR() {
        mTrackable = ARImageTrackable()
        mTrackable!!.loadFromPath(mTarget)
        val trackableManager = ARImageTracker.getInstance()
        trackableManager.addTrackable(mTrackable)

        mFirstImageNode = ARImageNode()
        mFirstImageNode!!.initWithPath(mMarker)
        mTrackable!!.world.addChild(mFirstImageNode)
        mTrackable!!.getWorld().getChildren()[0].setVisible(true)
    }
}
