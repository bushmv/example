package com.bush_example_app.bushv.example.data

import android.content.SharedPreferences
import android.graphics.Color
import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.presentation.weekStatistics.WeekStatistics

// class singleton for saved settings and user data,
// save in sharedPreferences when app going to close and restore when app start
object AppPref {

    private const val startEnglishLevelKey = "startEnglishLevel"
    var startEnglishLevel: Int = 0

    private const val colorStrKey = "colorStr"
    var colorStr = ""
    val color: Int
    get() = Color.parseColor(colorStr)

    private const val isDatabaseExistKey = "isDatabaseExist"
    var isDatabaseExist = false

    private const val isFirstStartKey = "isFirstStart"
    var isFirstStart = false

    private const val showSplashScreenKey = "showSplashScreen"
    var showSplashScreen = false

    private const val lastSavedLoginDataKey = "lastSavedLoginData"
    var lastSavedLoginData = ""

    private const val weekValuesKey = "weekValues"
    var weekValues = ""
    lateinit var weekStatistics: WeekStatistics

    // total and completed themes for each english level
    private const val beginnerThemesCountKey = "beginnerThemesCount"
    var beginnerThemesCount = 0
    private const val beginnerCompletedThemesCountKey = "beginnerCompletedThemesCount"
    var beginnerCompletedThemesCount = 0

    private const val elementaryThemesCountKey = "elementaryThemesCount"
    var elementaryThemesCount = 0
    private const val elementaryCompletedThemesCountKey = "elementaryCompletedThemesCount"
    var elementaryCompletedThemesCount = 0

    private const val intermediateThemesCountKey = "intermediateThemesCount"
    var intermediateThemesCount = 0
    private const val intermediateCompletedThemesCountKey = "intermediateCompletedThemesCount"
    var intermediateCompletedThemesCount = 0

    private const val upperIntermediateThemesCountKey = "upperIntermediateThemesCount"
    var upperIntermediateThemesCount = 0
    private const val upperIntermediateCompletedThemesCountKey = "upperIntermediateCompletedThemesCount"
    var upperIntermediateCompletedThemesCount = 0

    private const val advancedThemesCountKey = "advancedThemesCount"
    var advancedThemesCount = 0
    private const val advancedCompletedThemesCountKey = "advancedCompletedThemesCount"
    var advancedCompletedThemesCount = 0

    // total and completed examples for each english level
    private const val beginnerExamplesCountKey = "beginnerExamplesCount"
    var beginnerExamplesCount = 0
    private const val beginnerCompletedExamplesCountKey = "beginnerCompletedExamplesCount"
    var beginnerCompletedExamplesCount = 0

    private const val elementaryExamplesCountKey = "elementaryExamplesCount"
    var elementaryExamplesCount = 0
    private const val elementaryCompletedExamplesCountKey = "elementaryCompletedExamplesCount"
    var elementaryCompletedExamplesCount = 0

    private const val intermediateExamplesCountKey = "intermediateExamplesCount"
    var intermediateExamplesCount = 0
    private const val intermediateCompletedExamplesCountKey = "intermediateCompletedExamplesCount"
    var intermediateCompletedExamplesCount = 0

    private const val upperIntermediateExamplesCountKey = "upperIntermediateExamplesCount"
    var upperIntermediateExamplesCount = 0
    private const val upperIntermediateCompletedExamplesCountKey = "upperIntermediateCompletedExamplesCount"
    var upperIntermediateCompletedExamplesCount = 0

    private const val advancedExamplesCountKey = "advancedExamplesCount"
    var advancedExamplesCount = 0
    private const val advancedCompletedExamplesCountKey = "advancedCompletedExamplesCount"
    var advancedCompletedExamplesCount = 0

    val totalCompletedExamplesInApp: Int
        get() {
            return beginnerCompletedExamplesCount +
                    elementaryCompletedExamplesCount +
                    intermediateCompletedExamplesCount +
                    upperIntermediateCompletedExamplesCount +
                    advancedCompletedExamplesCount
        }

    val totalExamplesInApp: Int
        get() {
            return beginnerExamplesCount +
                    elementaryExamplesCount +
                    intermediateExamplesCount +
                    upperIntermediateExamplesCount +
                    advancedExamplesCount
        }

    fun read(pref: SharedPreferences) {
        pref.apply {
            colorStr = getString(colorStrKey, "#0000CC")!!
            isDatabaseExist = getBoolean(isDatabaseExistKey, false)
            startEnglishLevel = getInt(startEnglishLevelKey, 0)
            isFirstStart = getBoolean(isFirstStartKey, true)
            showSplashScreen = getBoolean(showSplashScreenKey, true)
            weekValues = getString(weekValuesKey, "0|0|0|0|0|0|0")!!
            lastSavedLoginData = getString(lastSavedLoginDataKey, "01/01/2021")!!
            //themes
            beginnerThemesCount = getInt(beginnerThemesCountKey, 0)
            beginnerCompletedThemesCount = getInt(beginnerCompletedThemesCountKey, 0)
            elementaryThemesCount = getInt(elementaryThemesCountKey, 0)
            elementaryCompletedThemesCount = getInt(elementaryCompletedThemesCountKey, 0)
            intermediateThemesCount = getInt(intermediateThemesCountKey, 0)
            intermediateCompletedThemesCount = getInt(intermediateCompletedThemesCountKey, 0)
            upperIntermediateThemesCount = getInt(upperIntermediateThemesCountKey, 0)
            upperIntermediateCompletedThemesCount = getInt(upperIntermediateCompletedThemesCountKey, 0)
            advancedThemesCount = getInt(advancedThemesCountKey, 0)
            advancedCompletedThemesCount = getInt(advancedCompletedThemesCountKey, 0)
            //examples
            beginnerExamplesCount = getInt(beginnerExamplesCountKey, 0)
            beginnerCompletedExamplesCount = getInt(beginnerCompletedExamplesCountKey, 0)
            elementaryExamplesCount = getInt(elementaryExamplesCountKey, 0)
            elementaryCompletedExamplesCount = getInt(elementaryCompletedExamplesCountKey, 0)
            intermediateExamplesCount = getInt(intermediateExamplesCountKey, 0)
            intermediateCompletedExamplesCount = getInt(intermediateCompletedExamplesCountKey, 0)
            upperIntermediateExamplesCount = getInt(upperIntermediateExamplesCountKey, 0)
            upperIntermediateCompletedExamplesCount = getInt(upperIntermediateCompletedExamplesCountKey, 0)
            advancedExamplesCount = getInt(advancedExamplesCountKey, 0)
            advancedCompletedExamplesCount = getInt(advancedCompletedExamplesCountKey, 0)
        }
    }
    fun save(pref: SharedPreferences) {
        pref.edit().apply {
            putString(colorStrKey, colorStr)
            putBoolean(isDatabaseExistKey, isDatabaseExist)
            putInt(startEnglishLevelKey, startEnglishLevel)
            putBoolean(isFirstStartKey, isFirstStart)
            putBoolean(showSplashScreenKey, showSplashScreen)
            putString(lastSavedLoginDataKey, weekStatistics.saveLastLogin())
            putString(weekValuesKey, weekStatistics.saveValues())
            //themes
            putInt(beginnerThemesCountKey, beginnerThemesCount)
            putInt(beginnerCompletedThemesCountKey, beginnerCompletedThemesCount)
            putInt(elementaryThemesCountKey, elementaryThemesCount)
            putInt(elementaryCompletedThemesCountKey, elementaryCompletedThemesCount)
            putInt(intermediateThemesCountKey, intermediateThemesCount)
            putInt(intermediateCompletedThemesCountKey, intermediateCompletedThemesCount)
            putInt(upperIntermediateThemesCountKey, upperIntermediateThemesCount)
            putInt(upperIntermediateCompletedThemesCountKey, upperIntermediateCompletedThemesCount)
            putInt(advancedThemesCountKey, advancedThemesCount)
            putInt(advancedCompletedThemesCountKey, advancedCompletedThemesCount)
            //examples
            putInt(beginnerExamplesCountKey, beginnerExamplesCount)
            putInt(beginnerCompletedExamplesCountKey, beginnerCompletedExamplesCount)
            putInt(elementaryExamplesCountKey, elementaryExamplesCount)
            putInt(elementaryCompletedExamplesCountKey, elementaryCompletedExamplesCount)
            putInt(intermediateExamplesCountKey, intermediateExamplesCount)
            putInt(intermediateCompletedExamplesCountKey, intermediateCompletedExamplesCount)
            putInt(upperIntermediateExamplesCountKey, upperIntermediateExamplesCount)
            putInt(upperIntermediateCompletedExamplesCountKey, upperIntermediateCompletedExamplesCount)
            putInt(advancedExamplesCountKey, advancedExamplesCount)
            putInt(advancedCompletedExamplesCountKey, advancedCompletedExamplesCount)
        }
        .apply()
    }

    fun increaseCompletedThemes(level: EnglishLevel) {
        when(level) {
            EnglishLevel.BEGINNER -> beginnerCompletedThemesCount++
            EnglishLevel.ELEMENTARY -> elementaryCompletedThemesCount++
            EnglishLevel.INTERMEDIATE -> intermediateCompletedThemesCount++
            EnglishLevel.UPPER_INTERMEDIATE -> upperIntermediateCompletedThemesCount++
            EnglishLevel.ADVANCED -> advancedCompletedThemesCount++
        }
    }

    fun increaseCompletedExample(level: EnglishLevel) {
        weekStatistics.increase()
        when(level) {
            EnglishLevel.BEGINNER -> beginnerCompletedExamplesCount++
            EnglishLevel.ELEMENTARY -> elementaryCompletedExamplesCount++
            EnglishLevel.INTERMEDIATE -> intermediateCompletedExamplesCount++
            EnglishLevel.UPPER_INTERMEDIATE -> upperIntermediateCompletedExamplesCount++
            EnglishLevel.ADVANCED -> advancedCompletedExamplesCount++
        }
    }

    fun change(level: String, examplesCount: Int) {
        when(level) {
            "0" -> { beginnerThemesCount++; beginnerExamplesCount += examplesCount; }
            "1" -> { elementaryThemesCount++; elementaryExamplesCount += examplesCount }
            "2" -> { intermediateThemesCount++; intermediateExamplesCount += examplesCount }
            "3" -> { upperIntermediateThemesCount++; upperIntermediateExamplesCount += examplesCount }
            "4" -> { advancedThemesCount++; advancedExamplesCount += examplesCount }
        }
    }
}