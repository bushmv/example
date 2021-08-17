package com.example.bushv.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.bushv.example.domain.entity.Example
import com.example.bushv.example.domain.entity.Status

@Dao
interface ExampleDao {

    @Query("SELECT * FROM example WHERE themeID = :themeID")
    suspend fun loadExamplesWithThemeId(themeID: Int): List<Example>

    @Query("SELECT * FROM example WHERE isFavorite = :isFavorite AND status = :status")
    suspend fun favoritesExample(isFavorite: Boolean = true, status: Status = Status.COMPLETED): List<Example>

    @Update
    fun updateExample(example: Example)

}