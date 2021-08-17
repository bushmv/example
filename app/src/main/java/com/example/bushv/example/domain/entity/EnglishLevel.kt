package com.example.bushv.example.domain.entity

enum class EnglishLevel(val strColor: String) {
    BEGINNER("#D9EAD3"),
    ELEMENTARY("#365628"),
    INTERMEDIATE("#FFE599"),
    UPPER_INTERMEDIATE("#F6B26B"),
    ADVANCED("#A30000");

    fun next(): EnglishLevel {
        return when(this) {
            BEGINNER -> ELEMENTARY
            ELEMENTARY -> INTERMEDIATE
            INTERMEDIATE -> UPPER_INTERMEDIATE
            UPPER_INTERMEDIATE -> ADVANCED
            ADVANCED -> throw IllegalStateException("No exist next level for ${this.name}")
        }
    }
}