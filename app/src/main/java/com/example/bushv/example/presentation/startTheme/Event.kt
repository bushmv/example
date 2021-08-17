package com.example.bushv.example.presentation.startTheme

import android.text.SpannableString
import android.text.SpannableStringBuilder

sealed class Event {
    class NewExample(val sentenceRU: SpannableString, val sentenceEN: SpannableStringBuilder, val isFavorite: Boolean) : Event()
    class UpdateSentenceEN(val sentenceEN: SpannableStringBuilder) : Event()
    object ThemeCompleted : Event()
    class ProgressChanged(val newProgress: Int) : Event()
    class IsFavoriteChanged(val isFavorite: Boolean) : Event()
}