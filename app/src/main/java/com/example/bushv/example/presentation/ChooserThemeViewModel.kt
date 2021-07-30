package com.example.bushv.example.presentation

import androidx.lifecycle.ViewModel
import com.example.bushv.example.domain.entity.EnglishLevel
import com.example.bushv.example.domain.entity.Status
import com.example.bushv.example.domain.entity.Theme

class ChooserThemeViewModel: ViewModel() {

    val themes: ArrayList<Theme> = arrayListOf(
        Theme(0, "theme 1 title/title", "theme 1 info", EnglishLevel.BEGINNER, Status.THEORY, 0, 5, 10),
        Theme(0, "theme 2 title/title", "theme 2 info", EnglishLevel.ADVANCED, Status.THEORY, 0, 5, 10),
        Theme(0, "theme 3 title/title", "theme 3 info", EnglishLevel.INTERMEDIATE, Status.THEORY, 0, 5, 10)
    )

}