package com.bush_example_app.bushv.example.presentation.weekStatistics

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

const val delimiter = "|"
const val daysOfWeek = 7
val shortDaysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

class WeekStatistics(
    lastSavedLoginDate: String,
    private val nowDate: String,
    savedValues: String,
    val dayOfWeek: Int
) {

    inner class Day {
        var title: String = ""
        var height: Int = -1
    }

    private val values: ArrayList<Int> = ArrayList(daysOfWeek)

    init {
        savedValues.split(delimiter).forEach {
            values.add(Integer.parseInt(it))
        }
        if (lastSavedLoginDate != nowDate) {

            val daySinceLastLogin = daySinceLastLogin(lastSavedLoginDate, nowDate)

            if (daySinceLastLogin < 7) {
                var current = dayOfWeek
                repeat(daySinceLastLogin) {
                    (current + daysOfWeek) % daysOfWeek
                    values[index(current)] = 0
                    current--
                }
                values[index(dayOfWeek)] = 0
            }  else {
                for (i in 0 until values.size) { values[i] = 0 }
            }
        }
    }

     private fun daySinceLastLogin(start: String, end: String): Int {
        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy")

        val lastDateLogin = simpleDateFormat.parse(start)
        val nowDate = simpleDateFormat.parse(end)

        //milliseconds
        val different: Long = nowDate.time - lastDateLogin.time

        val secondsInMilli = 1000L
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        return (different / (daysInMilli)).toInt()
    }

    fun saveValues(): String {
        val sb = StringBuilder()
        values.forEach {
            sb.append(it).append(delimiter)
        }
        sb.deleteAt(sb.length - 1)
        return sb.toString()
    }

    fun saveLastLogin(): String = nowDate

    fun increase() {
        values[index(dayOfWeek)]++
    }

    private fun index(oldIndex: Int): Int = (oldIndex + 5) % daysOfWeek

    fun calculatedGraphValues(): ArrayList<Day> {
        val result = arrayListOf<Day>().also { result -> repeat(daysOfWeek) { result.add(Day()) } }
        val cal = Calendar.getInstance()

        result[index(cal.get(Calendar.DAY_OF_WEEK))].title = formTitle(cal)
        repeat (6) {
            cal.add(Calendar.DAY_OF_MONTH, -1)
            result[index(cal.get(Calendar.DAY_OF_WEEK))].title = formTitle(cal)
        }

        val max = values.maxOf { it }
        values.withIndex().forEach {
            result[it.index].height = if (max == 0) 0 else (it.value * 100.0 / max).roundToInt()
        }
        return result
    }

    private fun formTitle(calendar: Calendar): String {
        val index = index(calendar.get(Calendar.DAY_OF_WEEK))
        val shortDayOfWeek = shortDaysOfWeek[index]
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        return "${shortDayOfWeek}\n${dayOfMonth}:${month}\n${values[index]}"
    }
}