package com.bush_example_app.bushv.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bush_example_app.bushv.example.domain.entity.EnglishLevel
import com.bush_example_app.bushv.example.domain.entity.Status
import com.bush_example_app.bushv.example.domain.entity.Theme

@Dao
interface ThemeDao {

    @Query("SELECT * FROM theme WHERE englishLevel = :englishLevel and status != :status LIMIT 1 OFFSET :offset")
    suspend fun loadTheme(englishLevel: EnglishLevel, offset: Int, status: Status = Status.COMPLETED): Theme

    @Query("SELECT COUNT(*) FROM theme WHERE englishLevel = :englishLevel AND status != :status")
    suspend fun themesCount(englishLevel: EnglishLevel, status: Status = Status.COMPLETED): Int

    @Update
    fun updateTheme(theme: Theme)

    @Query("SELECT * FROM theme WHERE englishLevel = :englishLevel AND status = :status")
    fun completedThemeFor(englishLevel: EnglishLevel, status: Status = Status.COMPLETED): List<Theme>

}