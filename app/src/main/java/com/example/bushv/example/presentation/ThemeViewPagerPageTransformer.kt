package com.example.bushv.example.presentation

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ThemeViewPagerPageTransformer: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            if (position != 0f) {
                scaleX = 1 - (abs(position / 4))
                scaleY = 1 - (abs(position / 2))
                alpha = 1.1f - (abs(position))
            }
        }
    }
}