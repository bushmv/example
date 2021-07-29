package com.example.bushv.example.domain.entity

enum class EnglishLevel {
    BEGINNER,
    ELEMENTARY,
    INTERMEDIATE,
    UPPER_INTERMEDIATE,
    ADVANCED;

    fun next(): EnglishLevel {
        return when(this) {
            BEGINNER -> ELEMENTARY
            ELEMENTARY -> INTERMEDIATE
            INTERMEDIATE -> UPPER_INTERMEDIATE
            UPPER_INTERMEDIATE -> ADVANCED
            ADVANCED -> throw IllegalStateException("No exist next level for ${this.name}")
        }
    }

    companion object {

        fun startLevel(): EnglishLevel {
            return BEGINNER
        }

        fun lastLevel(): EnglishLevel {
            return ADVANCED
        }
    }
}