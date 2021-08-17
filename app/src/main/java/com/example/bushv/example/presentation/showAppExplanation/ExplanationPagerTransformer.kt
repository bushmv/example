package com.example.bushv.example.presentation.showAppExplanation

import android.view.View
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.bushv.example.R
import kotlin.math.abs

class ExplanationPagerTransformer(): ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            if (position != 0f) {
                page.findViewById<CardView>(R.id.explanation_text_card_wrapper).apply {
                    this.translationY = abs(position * 256)
                }
            }
        }
    }
}