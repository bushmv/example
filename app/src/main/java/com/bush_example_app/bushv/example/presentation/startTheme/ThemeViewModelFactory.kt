package com.bush_example_app.bushv.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bush_example_app.bushv.example.domain.entity.Theme
import com.bush_example_app.bushv.example.presentation.startTheme.ThemeViewModel

class ThemeViewModelFactory(val theme: Theme): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ThemeViewModel(theme) as T
    }
}