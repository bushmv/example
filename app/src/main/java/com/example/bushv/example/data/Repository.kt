package com.example.bushv.example.data

import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
const val cardsCount = 5

class Repository(private val appDatabase: AppDatabase) {

    private val repositoryCache = RepositoryCache()
    private lateinit var loadIterator: ThemeLoadIterator

    companion object {
        private var repository: Repository? = null

        fun init(appDatabase: AppDatabase) {
            repository = Repository(appDatabase)
        }

        fun instance(): Repository {
            return repository ?: throw IllegalStateException("Repository must not be null")
        }
    }

    suspend fun initCache() {
        loadIterator = ThemeLoadIterator(appDatabase)

        for (i in AppPref.startEnglishLevel until EnglishLevel.values().size) {
            val countThemes = appDatabase.themesCount(EnglishLevel.values()[i])
            loadIterator.setThemeCounts(EnglishLevel.values()[i], countThemes)
        }

        val themes: ArrayList<Theme> = ArrayList(cardsCount)
        while (themes.size < cardsCount && loadIterator.hasNextForLoad()) {
            val theme = loadIterator.loadNext()
            themes.add(theme)
        }

        themes.forEach {
            val examples = appDatabase.loadExamplesWithThemeId(it.id) as ArrayList<Example>
            repositoryCache.saveExamples(it.id, examples)
        }
        repositoryCache.saveThemes(themes)

        if (loadIterator.hasNextForLoad()) {
            val theme = loadIterator.loadNext()
            val examples = appDatabase.loadExamplesWithThemeId(theme.id) as ArrayList<Example>

            repositoryCache.saveCachedNextTheme(theme)
            repositoryCache.saveCachedNextExamples(examples)
        }
    }

    fun themes(): ArrayList<Theme> = repositoryCache.cachedThemes()
    fun examplesForTheme(theme: Theme): ArrayList<Example> = repositoryCache.cachedExamplesForTheme(theme)

    suspend fun loadExamplesForTheme(theme: Theme): ArrayList<Example> =
        appDatabase.loadExamplesWithThemeId(theme.id) as ArrayList<Example>
    fun hasNextTheme(): Boolean  = repositoryCache.hasNextTheme()

    fun themeJustCompleted(completedTheme: Theme) {
        val completedExamples = repositoryCache.cachedExamplesForTheme(completedTheme)
        repositoryCache.update(completedTheme)

        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.updateTheme(completedTheme)
            completedExamples.forEach { appDatabase.updateExample(it) }
            loadIterator.decreaseOffset(completedTheme.level)
            if (loadIterator.hasNextForLoad()) {
                val nextTheme = loadIterator.loadNext()
                val examplesForNextTheme = appDatabase.loadExamplesWithThemeId(nextTheme.id) as ArrayList<Example>
                repositoryCache.saveCachedNextTheme(nextTheme)
                repositoryCache.saveCachedNextExamples(examplesForNextTheme)
            }
        }
    }

    suspend fun loadCompletedThemesFor(englishLevel: EnglishLevel): ArrayList<Theme> {
        if (repositoryCache.hasCompletedThemesFor(englishLevel)) {
            return repositoryCache.completedThemesFor(englishLevel)
        }
        val completedThemes = appDatabase.completedThemesFor(englishLevel) as ArrayList<Theme>
        repositoryCache.saveCompletedThemesFor(englishLevel, completedThemes)
        return completedThemes
    }

    suspend fun loadFavoriteExamples(): ArrayList<Example> {
        if (repositoryCache.hasCachedFavoriteExamples()) {
            return repositoryCache.favoriteExamples()
        }
        val favoriteExamples = appDatabase.favoriteExamples() as ArrayList<Example>
        repositoryCache.saveFavoriteExamples(favoriteExamples)
        return repositoryCache.favoriteExamples()
    }

    suspend fun save() {
        repositoryCache.cachedThemes().forEach { theme ->
            repositoryCache.cachedExamplesForTheme(theme).forEach { example ->
                appDatabase.updateExample(example)
            }
            appDatabase.updateTheme(theme)
        }
    }
}