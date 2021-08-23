package com.bush_example_app.bushv.example.data

import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Status
import com.bush_example_app.bushv.example.domain.entity.Theme

class TestAppDatabase: AppDatabase{

    val englishLevelsLoadedThemes = arrayListOf<EnglishLevel>()

    companion object {
        val mockTheme = Theme(-1, "", "", EnglishLevel.BEGINNER, Status.THEORY, 0, 0, 0f)
    }

    override suspend fun loadTheme(englishLevel: EnglishLevel, offset: Int): Theme {
        englishLevelsLoadedThemes.add(englishLevel)
        return mockTheme
    }

    override suspend fun themesCount(englishLevel: EnglishLevel): Int {
        return 1
    }

    override suspend fun updateTheme(theme: Theme) {}

    override suspend fun completedThemesFor(englishLevel: EnglishLevel): List<Theme> { return emptyList() }

    override suspend fun loadExamplesWithThemeId(themeId: Int): List<Example> { return emptyList() }

    override suspend fun updateExample(example: Example) {}

    override suspend fun favoriteExamples(): List<Example> { return emptyList() }
}