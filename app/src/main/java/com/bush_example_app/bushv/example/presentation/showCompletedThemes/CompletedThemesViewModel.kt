package com.bush_example_app.bushv.example.presentation.showCompletedThemes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bush_example_app.bushv.example.data.Repository
import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Theme
import kotlinx.coroutines.launch

class CompletedThemesViewModel: ViewModel() {

    private val repository = Repository.instance()
    private val _completedThemes = MutableLiveData<ArrayList<Theme>>()

    val completedThemesLiveData: LiveData<ArrayList<Theme>> = _completedThemes

    fun loadThemesFor(englishLevel: EnglishLevel) {
        viewModelScope.launch {
            val completedThemes = repository.loadCompletedThemesFor(englishLevel)
            _completedThemes.value = completedThemes
        }
    }
}