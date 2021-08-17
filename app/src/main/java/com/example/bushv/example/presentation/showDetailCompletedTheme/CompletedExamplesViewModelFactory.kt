package com.example.bushv.example.presentation.showDetailCompletedTheme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bushv.example.domain.entity.Theme

class CompletedExamplesViewModelFactory(val theme: Theme): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetailCompletedThemeViewModel(theme) as T
    }
}