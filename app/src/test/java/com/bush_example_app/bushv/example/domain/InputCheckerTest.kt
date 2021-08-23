package com.bush_example_app.bushv.example.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class InputCheckerTest {

    private lateinit var inputChecker: InputChecker
    private lateinit var sb: StringBuilder
    private var index: Int = -1
    private var completed: Boolean = false

    @Before
    fun reset() {
        inputChecker = InputChecker(
            { onStart(it) },
            { index, sb -> onUpdate(index, sb) },
            { onUpdate() })
        this.sb = StringBuilder()
        this.index = -1
        this.completed = false
    }


    @Test
    fun `input checker must skip not letter symbols`() {
        val testString = "it's ?? simple??!!"
        inputChecker.prepareFirstString(testString, testString)
        val userInput = testString
            .replace("'", "")
            .replace("?", "")
            .replace("!", "")

        userInput.forEach { inputChecker.check(it) }

        Assert.assertEquals(true, completed)
    }

    @Test
    fun `input checker must right display displayed string`() {
        val original = "Example string"
        val displayed = "Ex_m_e s___ng"
        inputChecker.prepareFirstString(original, displayed)
        val userInput = "exam"
        val resultDisplayedString = "Exam_e s___ng"

        userInput.forEach { inputChecker.check(it) }

        Assert.assertEquals(resultDisplayedString, sb.toString())
    }

    @Test
    fun `input checker must save user input substring when change displayed string`() {
        val original = "Example string"
        val displayed = "E__mp__ s___ng"
        inputChecker.prepareFirstString(original, displayed)
        val userInput = "bush_example_app"
        val resultDisplayedString = "Example s_ri__"

        userInput.forEach { inputChecker.check(it)}
        inputChecker.changeDisplayedString("E___ple s_ri__")

        Assert.assertEquals(resultDisplayedString, sb.toString())
    }

    @Test
    fun `input checker must do nothing when user input wrong symbols`() {
        val original = "Example string"
        val displayed = "Exa___ s___ng"
        inputChecker.prepareNextExample(original, displayed)
        val resultDisplayedString = displayed
        val userInput = "aporinmkglqqwzkhju"

        userInput.forEach { inputChecker.check(it) }
        println(sb.toString())

        Assert.assertEquals(resultDisplayedString, sb.toString())
    }



    private fun onStart(sb: StringBuilder) {
        this.sb = sb
    }

    private fun onUpdate(index: Int, sb: StringBuilder) {
        this.index = index
        this.sb = sb
    }

    private fun onUpdate() {
        this.completed = true
    }

}