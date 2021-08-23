package com.bush_example_app.bushv.example.presentation.startTheme

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressBarAnimation(
    private val progressBar: ProgressBar,
    var from: Int,
    var to: Int
) : Animation() {

    fun changeRange(newTo: Int) {
        from = to
        to = newTo
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value: Float = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
    }
}