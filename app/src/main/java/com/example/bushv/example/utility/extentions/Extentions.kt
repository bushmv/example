package com.example.bushv.example.utility.extentions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView

// add string span between "{" and "}" symbols
fun String.addForegroundSpan(color: Int): SpannableString {
    val startSpan = this.indexOf('{')
    val endSpan = this.indexOf('}') - 1
    val pureString = this
        .replace("{", "")
        .replace("}", "")
    val value = SpannableString(pureString)
    value.setSpan(
        ForegroundColorSpan(color),
        startSpan,
        endSpan,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return value
}

fun TextView.animateReplacingText(replaceCallback: (TextView) -> Unit) {
    this.animate()
        .scaleY(0f)
        .setDuration(300)
        .withEndAction {
            replaceCallback(this)
            this.animate()
                .scaleY(1f)
                .setDuration(300)
                .start()
        }.start()
}