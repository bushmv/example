package com.bush_example_app.bushv.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Status

@Dao
interface ExampleDao {

    @Query("SELECT * FROM bush_example_app WHERE themeID = :themeID")
    suspend fun loadExamplesWithThemeId(themeID: Int): List<Example>

    @Query("SELECT * FROM bush_example_app WHERE isFavorite = :isFavorite AND status = :status")
    suspend fun favoritesExample(isFavorite: Boolean = true, status: Status = Status.COMPLETED): List<Example>

    @Update
    fun updateExample(example: Example)

}