package com.example.bushv.example.data

import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Theme

interface Database {

    // theme
    suspend fun themeWith(englishLevel: EnglishLevel, offset: Int): Theme
    suspend fun themeCount(englishLevel: EnglishLevel): Int
    suspend fun updateTheme(theme: Theme)
    suspend fun completedThemesFor(englishLevel: EnglishLevel): List<Theme>

    //examples
    suspend fun examplesByThemeId(themeId: Int): List<Example>
    suspend fun updateExample(example: Example)
    suspend fun favoriteExamples(): List<Example>

}