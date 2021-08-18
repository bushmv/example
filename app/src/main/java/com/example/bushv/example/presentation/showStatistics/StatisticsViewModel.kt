package com.example.bushv.example.presentation.showStatistics

import androidx.lifecycle.ViewModel
import com.example.bushv.example.data.AppPref
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.presentation.weekStatistics.WeekStatistics
import kotlin.math.roundToInt

class StatisticsViewModel: ViewModel() {

    private val beginnerPercent = AppPref.beginnerCompletedExamplesCount * 100f / AppPref.beginnerExamplesCount
    private val elementaryPercent = AppPref.elementaryCompletedExamplesCount * 100f / AppPref.elementaryExamplesCount
    private val intermediatePercent = AppPref.intermediateCompletedExamplesCount * 100f / AppPref.intermediateExamplesCount
    private val upperIntermediatePercent = AppPref.upperIntermediateCompletedExamplesCount * 100f / AppPref.upperIntermediateExamplesCount
    private val advancedPercent = AppPref.advancedCompletedExamplesCount * 100f / AppPref.advancedExamplesCount

    fun progressStringFor(level: EnglishLevel): String {
        return when(level) {
            EnglishLevel.BEGINNER -> "${level.name} (${checkOnNan(beginnerPercent)} %)"
            EnglishLevel.ELEMENTARY -> "${level.name} (${checkOnNan(elementaryPercent)} %)"
            EnglishLevel.INTERMEDIATE -> "${level.name} (${checkOnNan(intermediatePercent)} %)"
            EnglishLevel.UPPER_INTERMEDIATE -> "${level.name} (${checkOnNan(upperIntermediatePercent)} %)"
            EnglishLevel.ADVANCED -> "${level.name} (${checkOnNan(advancedPercent)} %)"
        }
    }

    fun completedToAllString(): String = "${AppPref.totalCompletedExamplesInApp} / ${AppPref.totalExamplesInApp}"

    fun progressFor(level: EnglishLevel): Int {
        return when(level) {
            EnglishLevel.BEGINNER -> checkOnNan(beginnerPercent)
            EnglishLevel.ELEMENTARY -> checkOnNan(elementaryPercent)
            EnglishLevel.INTERMEDIATE -> checkOnNan(intermediatePercent)
            EnglishLevel.UPPER_INTERMEDIATE -> checkOnNan(upperIntermediatePercent)
            EnglishLevel.ADVANCED -> checkOnNan(advancedPercent)
        }
    }

    private fun checkOnNan(percent: Float): Int {
        return if (!percent.isNaN()) percent.roundToInt() else 0
    }

    fun weekStatistics(): ArrayList<WeekStatistics.Day> {
        return AppPref.weekStatistics.calculatedGraphValues()
    }
}