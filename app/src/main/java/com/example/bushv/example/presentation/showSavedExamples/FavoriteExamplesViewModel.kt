package com.example.bushv.example.presentation.showSavedExamples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bushv.example.data.Repository
import com.example.bushv.example.domain.entity.Example
import kotlinx.coroutines.launch

class FavoriteExamplesViewModel: ViewModel() {

    private val repository: Repository = Repository.instance()
    private val _favoritesExamples = MutableLiveData<ArrayList<Example>>()
    val favoriteExamples: LiveData<ArrayList<Example>> = _favoritesExamples

    fun loadFavorites() {
        viewModelScope.launch {
            val favoriteExamples = repository.loadFavoriteExamples()
            _favoritesExamples.value = favoriteExamples
        }
    }
}