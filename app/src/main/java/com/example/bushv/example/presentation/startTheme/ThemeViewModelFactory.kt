package com.example.bushv.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bushv.example.domain.entity.Theme
import com.example.bushv.example.presentation.startTheme.ThemeViewModel

class ThemeViewModelFactory(val theme: Theme): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ThemeViewModel(theme) as T
    }
}