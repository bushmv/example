package com.bush_example_app.bushv.example.data

import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Theme

// ThemeLoadIterator loads themes from db and know about offset for each theme in english level in db table
class ThemeLoadIterator(private val database: AppDatabase) {

    private var countThemesForEachEnglishLevel: ArrayList<Pair<EnglishLevel, Int>> = ArrayList()
    private val offsets: ArrayList<Int> = ArrayList()

    // when theme has completed, next request to db return (N - 1) results and offset must be decrease by 1
    fun decreaseOffset(level: EnglishLevel) { offsets[level.ordinal]-- }

    init {
        repeat(EnglishLevel.values().count()) { offsets.add(0) }
    }

    fun setThemeCounts(level: EnglishLevel, count: Int) {
        countThemesForEachEnglishLevel.add(Pair(level, count))
    }

    fun hasNextForLoad(): Boolean {
        countThemesForEachEnglishLevel.forEach {
            if (it.second > 0) {
                return true
            }
        }
        return false
    }

    suspend fun loadNext(): Theme {
        countThemesForEachEnglishLevel.withIndex().forEach {
            if (it.value.second > 0) { // has themes for current english level
                countThemesForEachEnglishLevel[it.index] = Pair(it.value.first, it.value.second - 1)
                val nextTheme = database.loadTheme(it.value.first, offsets[it.index])
                offsets[it.index]++ // next theme for englishLevel will be loaded with more offset
                return nextTheme
            }
        }
        throw IllegalStateException("Next Theme not exist. Call \"hasNext()\" before next.")
    }
}