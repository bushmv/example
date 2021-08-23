package com.bush_example_app.bushv.example.domain

import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Status
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExampleCollectionTest {

    private var examples: ArrayList<Example> = ArrayList()

    @Before
    fun init() {
        examples = arrayListOf(
            Example(0, "", "", "", "", false, Status.THEORY, 1, 1),
            Example(0, "", "", "", "", false, Status.PRACTICE, 2, 1),
            Example(0, "", "", "", "", false, Status.THEORY, 3, 1),
            Example(0, "", "", "", "", false, Status.PRACTICE, 4, 1),
            Example(0, "", "", "", "", false, Status.THEORY, 5, 1)
        )
    }

    @Test
    fun `example collection must give all theory examples first`() {
        val exampleCollection = ExampleCollection(examples)
        val theoryList = arrayListOf<Example>()

        theoryList.add(exampleCollection.next())
        theoryList.add(exampleCollection.next())
        theoryList.add(exampleCollection.next())

        for (example in theoryList) {
            Assert.assertEquals(Status.THEORY, example.status)
        }
    }

    @Test
    fun `hasExamples works correct test`() {
        val exampleCollection = ExampleCollection(examples)
        var count = 0

        while (exampleCollection.hasExamples()) {
            val next = exampleCollection.next()
            exampleCollection.upExampleStatus(next)
            count++
        }

        Assert.assertEquals(8, count)
    }

    @Test
    fun `All examples must be in completed status when hasExamples return false`() {
        val exampleCollection = ExampleCollection(examples)

        while (exampleCollection.hasExamples()) {
            val next = exampleCollection.next()
            exampleCollection.upExampleStatus(next)
        }

        examples.forEach { Assert.assertEquals(Status.COMPLETED, it.status) }
    }

}