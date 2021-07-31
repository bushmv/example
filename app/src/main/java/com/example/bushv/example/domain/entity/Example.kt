package com.example.bushv.example.domain.entity

data class Example(
    val wordId: Int,
    val word: String,
    val wordTranslate: String,
    val sentenceEN: String,
    val sentenceRU: String,
    var isFavorite: Boolean,
    var status: Status,
    val themeId: Int,
    val timeToCompleteInSeconds: Int
) {

    fun upStatus() {
        this.status = this.status.next()
    }

}