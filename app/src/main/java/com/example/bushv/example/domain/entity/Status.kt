package com.example.bushv.example.domain.entity

enum class Status {
    THEORY,
    PRACTICE,
    COMPLETED;

    fun next(): Status {
        return when(this) {
            THEORY -> PRACTICE
            PRACTICE -> COMPLETED
            COMPLETED -> throw IllegalArgumentException("No exist next status for ${this.name} ")
        }
    }
}