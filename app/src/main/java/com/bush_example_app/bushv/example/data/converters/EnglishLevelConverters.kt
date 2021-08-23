package com.bush_example_app.bushv.example.data.converters

import androidx.room.TypeConverter
import com.bush_example_app.bushv.example.domain.entity.EnglishLevel

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