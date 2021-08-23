package com.bush_example_app.bushv.example.presentation.startTheme

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bush_example_app.bushv.example.R
import java.util.*

private const val firstLineOfKeyboard = "qwertyuiop"
private const val secondLineOfKeyboard = "asdfghjkl"
private const val thirdLineOfKeyboard = "zxcvbnm"
private const val buttonsInRow = 10
private const val margin = 16

class Keyboard(
    private val context: Context,
    private val container: View,
    private val clickListener: (View) -> Unit
) {
    init {
        container.translationY = 750f
    }

    fun createKeyboard() {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val buttonSize = (screenWidth - margin * 2) / buttonsInRow

        val layoutParams = LinearLayout.LayoutParams(buttonSize, buttonSize)

        val firstLineCharacters = firstLineOfKeyboard.uppercase(Locale.ROOT).toCharArray()
        inflateRow(firstLineCharacters, container.findViewById(R.id.first_line), layoutParams)
        val secondLineCharacters = secondLineOfKeyboard.uppercase(Locale.ROOT).toCharArray()
        inflateRow(secondLineCharacters, container.findViewById(R.id.second_line), layoutParams)
        val thirdLineCharacters = thirdLineOfKeyboard.uppercase(Locale.ROOT).toCharArray()
        inflateRow(thirdLineCharacters, container.findViewById(R.id.third_line), layoutParams)
    }

    private fun inflateRow(characters: CharArray, layout: LinearLayout, layoutParams: LinearLayout.LayoutParams) {
        characters.forEach { layout.addView(createButton(it, layoutParams)) }
    }

    private fun createButton(text: Char, layoutParams: LinearLayout.LayoutParams): TextView {
        return TextView(context).apply {
            this.text = text.toString()
            this.textSize = 26f
            this.setPadding(8, 8, 8, 8)
            this.layoutParams = layoutParams
            this.gravity = Gravity.CENTER
            setOnClickListener{
                it.animate()
                    .scaleX(1.5f)
                    .scaleY(1.5f)
                    .setDuration(150)
                    .withEndAction {
                        it.animate()
                            .scaleY(1f)
                            .scaleX(1f)
                            .setDuration(10)
                            .start()
                    }.start()
                clickListener(it)
            }
            this.setTextColor(Color.BLACK)
            this.typeface = Typeface.MONOSPACE
        }
    }

    fun showKeyboardAnimation() {
        container.animate()
            .translationYBy(-750f)
            .setDuration(500)
            .setStartDelay(250)
            .start()
    }

}