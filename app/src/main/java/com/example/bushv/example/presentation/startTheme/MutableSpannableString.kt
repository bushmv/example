package com.example.bushv.example.presentation.startTheme

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.example.bushv.example.data.AppPref

class MutableSpannableString(
    private val startSpan: Int,
    private val endSpan: Int
) {

    val spannableString = SpannableStringBuilder()

    fun updateSpan(to: Int = 0, sb: StringBuilder) {
        spannableString.clear()
        spannableString.append(sb)
        if (to > 0) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                to,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                to,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        spannableString.setSpan(
            ForegroundColorSpan(AppPref.color),
            startSpan,
            endSpan,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

}