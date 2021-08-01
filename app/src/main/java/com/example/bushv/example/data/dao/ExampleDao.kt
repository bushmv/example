package com.example.bushv.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.bushv.example.domain.entity.Example

@Dao
interface ExampleDao {

    @Query("SELECT * FROM example WHERE themeID = :themeID")
    suspend fun loadExamplesWithThemeId(themeID: Int): List<Example>

    @Query("SELECT * FROM example WHERE isFavorite = :isFavorite")
    suspend fun favoritesExample(isFavorite: Boolean = true): List<Example>

    @Update
    fun updateExample(example: Example)


}