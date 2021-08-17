package com.example.bushv.example.presentation.startTheme

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bushv.example.data.AppPref
import com.example.bushv.example.data.Repository
import com.example.bushv.example.domain.ExampleCollection
import com.example.bushv.example.domain.InputChecker
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Status
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.utility.extentions.addForegroundSpan
import java.util.*
import kotlin.math.roundToInt

class ThemeViewModel(val theme: Theme) : ViewModel() {

    private val repository = Repository.instance()
    private val examples: ArrayList<Example> =
        repository.examplesForTheme(theme)
    private val exampleCollection = ExampleCollection(examples)

    private var percentToReplaceChar: Float = 0.5f

    private var progress: Float = theme.progress.toFloat()
    private val progressStep: Float = 100f / (examples.size * 2)

    private lateinit var currentExample: Example
    private lateinit var spannableDisplayedString: MutableSpannableString

    private val _eventLiveData = MutableLiveData<Event>()
    val eventLiveData: LiveData<Event> = _eventLiveData

    private var isFirstRun = true

    private val inputChecker = InputChecker(
        { sb -> newExample(sb) },
        { index, sentence -> updateSpannableString(index, sentence) },
        { exampleCompleted() }
    )

    private fun newExample(sb: StringBuilder) {
        spannableDisplayedString.updateSpan(sb = sb)
        _eventLiveData.value = Event.NewExample(
            currentExample.sentenceRU.addForegroundSpan(Color.parseColor(AppPref.colorStr)),
            spannableDisplayedString.spannableString,
            currentExample.isFavorite
        )
        updateProgress()
    }

    private fun updateProgress() {
        if (isFirstRun) isFirstRun = false
        else progress += progressStep
        _eventLiveData.value = Event.ProgressChanged(progress.toInt())
        theme.progress = progress.roundToInt()
    }

    private fun updateSpannableString(index: Int, sb: StringBuilder) {
        spannableDisplayedString.updateSpan(index, sb)
        _eventLiveData.value = Event.UpdateSentenceEN(spannableDisplayedString.spannableString)
    }

    private fun exampleCompleted() {
        exampleCollection.upExampleStatus(currentExample)
        if (currentExample.status == Status.COMPLETED) {
            AppPref.increaseCompletedExample(theme.level)
        }
        theme.timeToComplete -= currentExample.sentenceEN.length
        if (exampleCollection.hasExamples()) {
            prepareNextExample()
        } else {
            theme.status = Status.COMPLETED
            theme.timeToComplete = 0f
            theme.progress = 100
            AppPref.increaseCompletedThemes(theme.level)
            _eventLiveData.value = Event.ThemeCompleted
        }
    }

    fun prepareNextExample() {
        currentExample = exampleCollection.next()
        val startWordSpan = currentExample.sentenceEN.indexOf('{')
        val endWordSpan = currentExample.sentenceEN.indexOf('}') - 1
        spannableDisplayedString = MutableSpannableString(startWordSpan, endWordSpan)
        val pureString = cleanString(currentExample.sentenceEN)
        val displayedString = formDisplayedString(pureString)
        inputChecker.prepareNextExample(pureString, displayedString)
    }

    private fun cleanString(str: String): String {
        return str
            .replace("{", "")
            .replace("}", "")
    }

    private fun formDisplayedString(pureString: String): String {
        return if (currentExample.status == Status.THEORY) {
            pureString
        } else {
            formDisplayedStringWithSpaces(pureString)
        }
    }

    private fun formDisplayedStringWithSpaces(pureString: String): String {
        val sb = StringBuilder(pureString.length)
        val random = Random()
        pureString.forEach {
            if (random.nextFloat() < percentToReplaceChar && it.isLetter()) {
                sb.append("_")
            } else {
                sb.append(it)
            }
        }
        return sb.toString()
    }

    fun check(char: Char) {
        inputChecker.check(char)
    }

    fun changeIsFavorite() {
        val newIsFavorite = !currentExample.isFavorite
        currentExample.isFavorite = newIsFavorite
        _eventLiveData.value = Event.IsFavoriteChanged(newIsFavorite)
    }

    fun changeDifficult(value: Float) {
        percentToReplaceChar = (value.toInt() - 1) * 0.25f // 0, 0.25, 0.5, 0.75 or 1
        val pureString = cleanString(currentExample.sentenceEN)
        val displayedString = formDisplayedString(pureString)
        inputChecker.changeDisplayedString(displayedString)
    }
}