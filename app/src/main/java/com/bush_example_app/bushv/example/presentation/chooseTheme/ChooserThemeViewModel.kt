package com.bush_example_app.bushv.example.presentation

import androidx.lifecycle.ViewModel
import com.bush_example_app.bushv.example.data.Repository
import com.bush_example_app.bushv.example.domain.entity.Theme

class ChooserThemeViewModel: ViewModel() {

    private val repository = Repository.instance()
    val themes = Repository.instance().themes()

    fun hasNextTheme(): Boolean = repository.hasNextTheme()

    fun themeJustCompleted(completedTheme: Theme) {
        repository.themeJustCompleted(completedTheme)
    }
}



