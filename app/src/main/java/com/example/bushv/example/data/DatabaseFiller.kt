package com.example.bushv.example.data

import android.content.ContentValues
import android.content.Context
import androidx.room.OnConflictStrategy
import androidx.sqlite.db.SupportSQLiteDatabase
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

private const val delimiter = "|"

// read all data from /assets/db and fill db when db create first time
class DatabaseFiller {

    private var themeId = 0
    private var exampleId = 0

    fun createRecord(fileFrom: String, db: SupportSQLiteDatabase, context: Context) {

        // format file:
        // first line - theme description
        // others lines - example description
        val lines: ArrayList<String> = fileToStringArray(fileFrom, context)
        val theme = lines[0]
        val examples = lines.subList(1, lines.size)

        var timeToComplete = 0f
        examples.forEach { timeToComplete += it.split(delimiter)[2].length * 2 }

        AppPref.change(theme.split(delimiter)[2], examples.size)

        saveTheme(theme, examples.size, timeToComplete, db)
        saveExamples(examples, db)
    }

    private fun fileToStringArray(fileName: String, applicationContext: Context): ArrayList<String> {
        val result = ArrayList<String>()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(applicationContext.assets.open(fileName), "UTF-8"))
            var line: String? = reader.readLine()
            while (line != null) {
                result.add(line)
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
        return result
    }

    private fun saveTheme(theme: String, examplesCount: Int, timeToComplete: Float,  db: SupportSQLiteDatabase) {

        // string format for theme:
        //        0       |  1 |      2
        // titleEN/titleRU|info|englishLevel

        val cn = ContentValues().apply {
            val fields = theme.split(delimiter)
            validateThemeFields(fields)
            put("id", ++themeId)
            put("title", fields[0])
            put("info", fields[1])
            put("englishLevel", fields[2])
            put("status", "0")
            put("progress", "0")
            put("examplesCount", examplesCount)
            put("timeToComplete", timeToComplete)
        }
        db.insert("theme", OnConflictStrategy.IGNORE, cn)
    }

    private fun validateThemeFields(fields: List<String>) {
        if (fields.size != 3)
            throw IllegalArgumentException("fields must have size 3(title, info, level), but size = ${fields.size}")
    }

    private fun saveExamples(examples: MutableList<String>, db: SupportSQLiteDatabase) {

        // file format for example
        //  0 |       1     |     2    |     3
        //word|wordTranslate|sentenceEN|sentenceRU

        val cn = ContentValues()
        examples.forEach {
            val fields = it.split(delimiter)
            validateExampleFields(fields)
            cn.clear()
            cn.apply {
                put("id", exampleId++)
                put("word", fields[0])
                put("wordTranslate", fields[1])
                put("sentenceEN", fields[2])
                put("sentenceRU", fields[3])
                put("isFavorite", false)
                put("status", "0")
                put("themeId", themeId)
                put("timeToCompleteInSeconds", fields[2].length * 2)
            }
            db.insert("example", OnConflictStrategy.IGNORE, cn)
        }
    }

    private fun validateExampleFields(fields: List<String>) {
        if (fields.size != 4)
            throw IllegalArgumentException(
                "${fields} must have size 4(word, wordTranslate, sentenceEN, sentenceRU), but size = ${fields.size}")
        if (!fields[2].contains("{") || !fields[2].contains("}")) {
            throw IllegalArgumentException("${fields[2]} must contains sentenceEN, that contains '{' and '}'")
        }
        if (!fields[3].contains("{") || !fields[3].contains("}")) {
            throw IllegalArgumentException("${fields[3]} must contains sentenceRU, that contains '{' and '}'")
        }
    }
}