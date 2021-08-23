package com.bush_example_app.bushv.example.data

import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Theme
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RepositoryCache {

    private var cachedThemes: ArrayList<Theme> = ArrayList()
    private var cachedExamplesByThemeId: MutableMap<Int, ArrayList<Example>> = HashMap()
    private var cachedCompletedThemesByEnglishLevel: MutableMap<EnglishLevel, ArrayList<Theme>> =
        EnumMap(EnglishLevel::class.java)
    private var cachedFavoriteExamples: ArrayList<Example> = arrayListOf()

    // this 2 field loaded before it will be needed for fast replace when current theme will be completed
    private var cachedNextTheme: Theme? = null
    private var cachedNextExamples: ArrayList<Example>? = null

    fun saveThemes(themes: ArrayList<Theme>) {
        cachedThemes = themes
    }

    fun saveExamples(themeIdKey: Int, examples: ArrayList<Example>) {
        cachedExamplesByThemeId[themeIdKey] = examples
    }

    fun saveCachedNextTheme(theme: Theme) {
        cachedNextTheme = theme
    }

    fun saveCachedNextExamples(examples: ArrayList<Example>) {
        cachedNextExamples = examples
    }

    fun hasNextTheme(): Boolean = cachedNextTheme != null

    fun cachedThemes() = cachedThemes
    fun cachedExamplesForTheme(theme: Theme): ArrayList<Example> =
        cachedExamplesByThemeId[theme.id] as ArrayList<Example>

    fun hasCompletedThemesFor(englishLevel: EnglishLevel): Boolean =
        cachedCompletedThemesByEnglishLevel.containsKey(englishLevel)

    fun saveCompletedThemesFor(englishLevel: EnglishLevel, themes: ArrayList<Theme>) {
        cachedCompletedThemesByEnglishLevel[englishLevel] = themes
    }

    fun completedThemesFor(englishLevel: EnglishLevel): ArrayList<Theme> {
        return cachedCompletedThemesByEnglishLevel[englishLevel]
            ?: throw IllegalStateException("Completed cached list at key $englishLevel in repositoryCache must not be null")
    }

    fun hasCachedFavoriteExamples(): Boolean = cachedFavoriteExamples.isNotEmpty()
    fun saveFavoriteExamples(favoriteExamples: ArrayList<Example>) {
        cachedFavoriteExamples = favoriteExamples
    }

    fun favoriteExamples(): ArrayList<Example> {
        // cachedExamplesByThemeId - list of themes and examples for each theme,
        // that can be (but may not be) is favorites list
        // cachedFavoriteExamples - cached favorites from db
        val favorites = ArrayList<Example>()
        cachedExamplesByThemeId.entries.forEach { entry ->
            favorites.addAll(entry.value.filter { it.isFavorite })
        }
        favorites.addAll(cachedFavoriteExamples.filter { it.isFavorite })
        return ArrayList(favorites)
    }

    fun update(completedTheme: Theme) {
        updateFavoritesExamples(completedTheme)
        updateCachedThemes(completedTheme)
        updateCompletedThemes(completedTheme)
        cachedNextTheme = null
        cachedNextExamples = null
    }

    private fun updateFavoritesExamples(completedTheme: Theme) {
        if (cachedFavoriteExamples.isNotEmpty()) {
            val favoritesExamplesCompletedTheme =
                cachedExamplesByThemeId[completedTheme.id]?.filter { it.isFavorite } as ArrayList<Example>
            cachedFavoriteExamples.addAll(favoritesExamplesCompletedTheme)
        }
    }

    private fun updateCachedThemes(completedTheme: Theme) {
        val indexCompletedTheme = cachedThemes.indexOf(completedTheme)
        cachedThemes.removeAt(indexCompletedTheme)
        cachedExamplesByThemeId.remove(completedTheme.id)
        cachedNextTheme?.let {
            cachedThemes.add(indexCompletedTheme, it)
            cachedExamplesByThemeId[it.id] = cachedNextExamples
                ?: throw IllegalStateException("Theme $it hasn't completed, but no one bush_example_app for it in database")
        }
    }

    private fun updateCompletedThemes(completedTheme: Theme) {
        cachedCompletedThemesByEnglishLevel[completedTheme.level]?.add(completedTheme)
    }
}