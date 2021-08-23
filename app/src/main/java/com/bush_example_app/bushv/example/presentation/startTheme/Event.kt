package com.bush_example_app.bushv.example.presentation.startTheme

import android.text.SpannableString
import android.text.SpannableStringBuilder
import com.bush_example_app.bushv.example.domain.entity.Status

sealed class Event {
    class NewExample(val sentenceRU: SpannableString, val sentenceEN: SpannableStringBuilder, val isFavorite: Boolean, val status: Status) : Event()
    class UpdateSentenceEN(val sentenceEN: SpannableStringBuilder) : Event()
    object ThemeCompleted : Event()
    class ProgressChanged(val newProgress: Int) : Event()
    class IsFavoriteChanged(val isFavorite: Boolean) : Event()
}