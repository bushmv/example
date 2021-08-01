package com.example.bushv.example.domain.entity

import androidx.room.*
import com.example.bushv.example.data.converters.StatusConverter

@Entity(tableName = "example", foreignKeys = [ForeignKey(
    entity = Theme::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("themeID")
)])
data class Example(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "wordTranslate") val wordTranslate: String,
    @ColumnInfo(name = "sentenceEN") val sentenceEN: String,
    @ColumnInfo(name = "sentenceRU") val sentenceRU: String,
    @ColumnInfo(name = "isFavorite", index = true) var isFavorite: Boolean,
    @TypeConverters(StatusConverter::class) @ColumnInfo(name = "status") var status: Status,
    @ColumnInfo(name = "themeID", index = true) val themeId: Int,
    @ColumnInfo(name = "timeToCompleteInSeconds") val timeToCompleteInSeconds: Int
) {

    @Ignore
    constructor(
        word: String,
        wordTranslate: String,
        sentenceEN: String,
        sentenceRU: String,
        themeId: Int
    ): this(0, word, wordTranslate, sentenceEN, sentenceRU, false, Status.THEORY, themeId, sentenceEN.length * 2)

    fun upStatus() {
        this.status = this.status.next()
    }

}