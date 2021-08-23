package com.bush_example_app.bushv.example.data

import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Theme

interface AppDatabase {

    // theme
    suspend fun loadTheme(englishLevel: EnglishLevel, offset: Int): Theme
    suspend fun themesCount(englishLevel: EnglishLevel): Int
    suspend fun updateTheme(theme: Theme)
    suspend fun completedThemesFor(englishLevel: EnglishLevel): List<Theme>

    //examples
    suspend fun loadExamplesWithThemeId(themeId: Int): List<Example>
    suspend fun updateExample(example: Example)
    suspend fun favoriteExamples(): List<Example>

}