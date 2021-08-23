package com.bush_example_app.bushv.example.presentation.chooseTheme

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.bush_example_app.bushv.example.R

class Dots(
    size: Int,
    private val context: Context,
    private val container: LinearLayout
) {

    private val dotsView: ArrayList<ImageView> = ArrayList(size)
    private var current = 0

    init {
        for (i in 0 until size) {
            val dotView = ImageView(context)
            dotView.setImageDrawable(
                ResourcesCompat.getDrawable(context.resources, R.drawable.dot_not_selected, context.applicationContext.theme))
            val lp = LinearLayout.LayoutParams(32, 32)
            lp.setMargins(16, 16, 16, 16)
            dotsView.add(dotView)
            container.addView(dotView, lp)
        }
        selectDot(0)
    }

    private fun selectDot(position: Int) {
        dotsView[position].setImageDrawable(
            ResourcesCompat.getDrawable(context.resources, R.drawable.dot_selected, context.applicationContext.theme))
    }

    private fun unselectDot(position: Int) {
        dotsView[position].setImageDrawable(
            ResourcesCompat.getDrawable(context.resources, R.drawable.dot_not_selected, context.applicationContext.theme)
        )
    }

    fun changeSelectedDot(newPosition: Int) {
        if (current < dotsView.size) unselectDot(current)
        current = newPosition
        selectDot(current)
    }

    fun removeDot(position: Int) {
        container.removeViewAt(position)
        dotsView.removeAt(position)
    }

}