package com.example.bushv.example.data.converters

import androidx.room.TypeConverter
import com.example.bushv.example.domain.entity.EnglishLevel

class EnglishLevelConverter {

    @TypeConverter
    fun toEnglishLevel(position: Int): EnglishLevel {
        return EnglishLevel.values()[position]
    }

    @TypeConverter
    fun toInt(englishLevel: EnglishLevel): Int {
        return englishLevel.ordinal
    }

}