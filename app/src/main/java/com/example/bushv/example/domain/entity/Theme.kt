package com.example.bushv.example.domain.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

// parcelable for transfer theme between fragments
@Entity(tableName = "theme")
@Parcelize
data class Theme(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "info") val info: String,
    @TypeConverters(EnglishLevel::class) @ColumnInfo(name = "englishLevel") val level: EnglishLevel,
    @ColumnInfo(name = "status") var status: Status,
    @ColumnInfo(name = "progress") var progress: Int,
    @ColumnInfo(name = "examplesCount") val examplesCount: Int,
    @ColumnInfo(name = "timeToComplete") var timeToComplete: Float
) : Parcelable {

    fun titleEN(): String {
        return title.split("/")[0]
    }

    fun titleRU(): String {
        return title.split("/")[1]
    }

}
