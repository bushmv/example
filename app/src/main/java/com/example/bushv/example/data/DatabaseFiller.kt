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
class DatabaseFirstTimeFiller {

    private var themeId = 0
    private var exampleId = 0

    fun createRecord(fileFrom: String, db: SupportSQLiteDatabase, context: Context) {

        // format file:
        // first line - theme description
        // others lines - example description

        val lines: ArrayList<String> = fileToStringArray(fileFrom, context)
        val theme = lines[0]
        val examples = lines.subList(1, lines.size)
        saveTheme(theme, examples.size, db)
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

    private fun saveTheme(theme: String, examplesCount: Int,  db: SupportSQLiteDatabase) {

        // string format for theme:
        // |       0      |  1 |      2     |       3
        // titleEN/titleRU|info|englishLevel|timeToComplete

        val cn = ContentValues().apply {
            val fields = theme.split(delimiter)
            put("id", ++themeId)
            put("title", fields[0])
            put("info", fields[1])
            put("englishLevel", fields[2])
            put("status", "0")
            put("progress", "0")
            put("examplesCount", examplesCount)
            put("timeToComplete", fields[3])
        }
        db.insert("theme", OnConflictStrategy.IGNORE, cn)
    }

    private fun saveExamples(examples: MutableList<String>, db: SupportSQLiteDatabase) {

        // file format for example
        //  0 |       1     |     2    |     3
        //word|wordTranslate|sentenceEN|sentenceRU

        val cn = ContentValues()

        examples.forEach {
            val fields = it.split(delimiter)
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
}