package com.bush_example_app.bushv.example.data

import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ThemeLoadIteratorTest {

    @Test
    fun `verify that load iterator hasNext will return false when no themes more`() {
        val testDatabase = TestAppDatabase()
        val themeLoadIterator = ThemeLoadIterator(testDatabase)
        themeLoadIterator.setThemeCounts(EnglishLevel.BEGINNER, 3)
        themeLoadIterator.setThemeCounts(EnglishLevel.INTERMEDIATE, 1)
        themeLoadIterator.setThemeCounts(EnglishLevel.ADVANCED, 2)

        repeat(6) { runBlocking { themeLoadIterator.loadNext() } }

        Assert.assertEquals(false, themeLoadIterator.hasNextForLoad())
    }

    @Test
    fun `verify that load iterator load themes in right order of english level`() {
        val testDatabase = TestAppDatabase()
        val themeLoadIterator = ThemeLoadIterator(testDatabase)
        themeLoadIterator.setThemeCounts(EnglishLevel.BEGINNER, 1)
        themeLoadIterator.setThemeCounts(EnglishLevel.INTERMEDIATE, 2)
        themeLoadIterator.setThemeCounts(EnglishLevel.UPPER_INTERMEDIATE, 1)
        themeLoadIterator.setThemeCounts(EnglishLevel.ADVANCED, 2)
        val rightOrderEnglishThemes = arrayListOf(
            EnglishLevel.BEGINNER,
            EnglishLevel.INTERMEDIATE,
            EnglishLevel.INTERMEDIATE,
            EnglishLevel.UPPER_INTERMEDIATE,
            EnglishLevel.ADVANCED,
            EnglishLevel.ADVANCED
        )

        repeat(6) { runBlocking { themeLoadIterator.loadNext() } }

        Assert.assertEquals(rightOrderEnglishThemes, testDatabase.englishLevelsLoadedThemes)
    }

}