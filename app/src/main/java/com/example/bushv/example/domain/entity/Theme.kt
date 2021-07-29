package com.example.bushv.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// parcelable for transfer theme between fragments
@Parcelize
data class Theme(
    val themeId: Int = 0,
    val title: String,
    val info: String,
    val level: EnglishLevel,
    var status: Status,
    var progress: Int,
    val wordCount: Int,
    val timeToComplete: Int
) : Parcelable
