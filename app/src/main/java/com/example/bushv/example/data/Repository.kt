package com.example.bushv.example.data

import com.example.bushv.example.data.dao.RepositoryCache
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        var englishLevel = EnglishLevel.startLevel()
        loadIterator = ThemeLoadIterator(appDatabase)

        while (englishLevel != EnglishLevel.lastLevel()) {
            val countThemes = appDatabase.themesCount(englishLevel)
            loadIterator.setThemeCounts(englishLevel, countThemes)
            englishLevel = englishLevel.next()
        }

        val themes: ArrayList<Theme> = ArrayList(3) // TODO 3 by default, change
        while (themes.size < 3 && loadIterator.hasNextForLoad()) {
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
    fun hasNextTheme(): Boolean  = repositoryCache.hasNextTheme()

    fun themeHasCompleted(completedTheme: Theme) {
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

    suspend fun save() {
        repositoryCache.cachedThemes().forEach { theme ->
            repositoryCache.cachedExamplesForTheme(theme).forEach { example ->
                appDatabase.updateExample(example)
            }
            appDatabase.updateTheme(theme)
        }
    }
}