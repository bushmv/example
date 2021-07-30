package com.example.bushv.example.domain

class InputChecker(
    private val onStartNewExample: (StringBuilder) -> Unit,
    private val onUpdateExample: (Int, StringBuilder) -> Unit,
    private val onCompleteExample: () -> Unit
) {
    private var index: Int = 0
    private val displayedString: StringBuilder = StringBuilder()
    private lateinit var originalString: String

    fun check(char: Char) {
        if (charNotMatches(char)) return
        updateDisplayedString()
        while (symbolIsNotLetter()) {
            updateDisplayedString()
        }
        if (exampleCompleted()) {
            onCompleteExample()
        } else {
            onUpdateExample(index, displayedString)
        }
    }

    private fun charNotMatches(char: Char): Boolean {
        return !(originalString[index].equals(char, true))
    }

    private fun updateDisplayedString() {
        displayedString[index] = originalString[index]
        index++
    }

    private fun symbolIsNotLetter(): Boolean {
        return !exampleCompleted() && !originalString[index].isLetter()
    }

    fun exampleCompleted(): Boolean = index >= originalString.length

    fun changeDisplayedString(newTypedString: String) {
        displayedString.clear()
        displayedString.append(originalString.substring(0, index))
        displayedString.append(newTypedString.substring(index, newTypedString.length))
        onUpdateExample(index, displayedString)
    }

    fun prepareFirstString(original: String, displayed: String): StringBuilder {
        reset(original, displayed)
        return displayedString
    }

    fun prepareNextString(original: String, displayed: String) {
        reset(original, displayed)
        onStartNewExample(displayedString)
    }

    private fun reset(original: String, displayed: String) {
        this.originalString = original
        this.displayedString.clear().append(displayed)
        index = 0
    }
}