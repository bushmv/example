package com.example.bushv.example.presentation.showDetailCompletedTheme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bushv.example.data.Repository
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Theme
import kotlinx.coroutines.launch

class DetailCompletedThemeViewModel(val theme: Theme): ViewModel() {

    private val repository = Repository.instance()
    private var _examplesLiveDate: MutableLiveData<ArrayList<Example>> = MutableLiveData()
    val examplesLiveDate: LiveData<ArrayList<Example>> = _examplesLiveDate

    init {
        viewModelScope.launch {
            val examples = repository.loadExamplesForTheme(theme)
            _examplesLiveDate.value = examples
        }
    }
}