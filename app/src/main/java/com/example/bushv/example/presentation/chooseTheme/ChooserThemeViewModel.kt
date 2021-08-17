package com.example.bushv.example.presentation

import androidx.lifecycle.ViewModel
import com.example.bushv.example.data.Repository
import com.example.bushv.example.domain.entity.Theme

class ChooserThemeViewModel: ViewModel() {

    private val repository = Repository.instance()
    val themes = Repository.instance().themes()

    fun hasNextTheme(): Boolean = repository.hasNextTheme()

    fun themeJustCompleted(completedTheme: Theme) {
        repository.themeJustCompleted(completedTheme)
    }
}



