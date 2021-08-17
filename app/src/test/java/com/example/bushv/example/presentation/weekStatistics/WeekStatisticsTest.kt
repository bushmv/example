package com.example.bushv.example.presentation.weekStatistics

import org.junit.Assert.assertEquals
import org.junit.Test

class WeekStatisticsTest {

    @Test
    fun `the case when user enters to app today and passes 3 examples`() {
        val weekStatistics =
            WeekStatistics("15/05/2021", "15/05/2021", "1|2|3|4|5|6|7", 4)

        repeat(3) {weekStatistics.increase()}

        assertEquals("1|2|6|4|5|6|7", weekStatistics.saveValues())
    }

    @Test
    fun `the case when user last time entered to app yesterday, enters now and passes 3 examples`() {
        val weekStatistics =
            WeekStatistics("15/05/2021", "16/05/2021", "1|2|3|4|5|6|7", 4)

        repeat(3) {weekStatistics.increase()}

        assertEquals("1|2|3|4|5|6|7", weekStatistics.saveValues())
    }

    @Test
    fun `the case when user last time entered to app a week ago, enters now and passes 3 examples`() {
        val weekStatistics =
            WeekStatistics("15/05/2021", "22/05/2021", "1|2|3|4|5|6|7", 4)

        repeat(3) {weekStatistics.increase()}

        assertEquals("0|0|3|0|0|0|0", weekStatistics.saveValues())
    }

    @Test
        fun `the test checks if the column heights of the graph are calculated correctly`() {
        val weekStatistics =
            WeekStatistics("15/05/2021", "16/05/2021", "0|2|0|0|0|0|0", 4)

        repeat(4) {weekStatistics.increase()}

        val graphValues = weekStatistics.calculatedGraphValues()
        assertEquals(50, graphValues[1].height)
        assertEquals(100, graphValues[2].height)
    }



}