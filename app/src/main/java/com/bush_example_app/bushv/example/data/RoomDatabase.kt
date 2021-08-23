package com.bush_example_app.bushv.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bush_example_app.bushv.example.data.converters.EnglishLevelConverter
import com.bush_example_app.bushv.example.data.converters.StatusConverter
import com.bush_example_app.bushv.example.data.dao.ExampleDao
import com.bush_example_app.bushv.example.data.dao.ThemeDao
import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Theme

@Database(entities = [Theme::class, Example::class], version = 1)
@TypeConverters(StatusConverter::class, EnglishLevelConverter::class)
abstract class AppRoomDatabase: RoomDatabase(), AppDatabase  {

    abstract fun themeDao(): ThemeDao
    abstract fun exampleDao(): ExampleDao

    override suspend fun loadTheme(englishLevel: EnglishLevel, offset: Int): Theme {
        return themeDao().loadTheme(englishLevel, offset)
    }

    override suspend fun themesCount(englishLevel: EnglishLevel): Int {
        return themeDao().themesCount(englishLevel)
    }

    override suspend fun updateTheme(theme: Theme) {
        themeDao().updateTheme(theme)
    }

    override suspend fun completedThemesFor(englishLevel: EnglishLevel): List<Theme> {
        return themeDao().completedThemeFor(englishLevel)
    }

    override suspend fun loadExamplesWithThemeId(themeId: Int): List<Example> {
        return exampleDao().loadExamplesWithThemeId(themeId)
    }

    override suspend fun updateExample(example: Example) {
        exampleDao().updateExample(example)
    }

    override suspend fun favoriteExamples(): List<Example> {
        return exampleDao().favoritesExample()
    }
}