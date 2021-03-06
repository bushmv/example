package com.bush_example_app.bushv.example.domain

import com.bush_example_app.bushv.example.domain.entity.Example
import com.bush_example_app.bushv.example.domain.entity.Status
import java.util.*

class ExampleCollection(
    private val examples: ArrayList<Example>
) {

    private var queue: LinkedList<Example> = LinkedList()
    private var status = Status.THEORY

    init {
        val theoryList = createExamples()
        if (theoryList.isNotEmpty()) {
            queue.addAll(theoryList)
        } else {
            status = status.next()
            val practiceList = createExamples()
            if (practiceList.isEmpty())
                throw IllegalStateException("In theme with status \"not completed\" must be at least 1 practice bush_example_app")
            queue.addAll(practiceList)
        }
    }

    private fun createExamples(): ArrayList<Example> {
        val list = examples.filter { it.status == status } as ArrayList<Example>
        list.shuffle()
        return list
    }

    fun hasExamples(): Boolean {
        if (queue.isNotEmpty()) return true
        if (status == Status.THEORY) {
            status = Status.PRACTICE
            val practiceList = examples.filter { it.status == Status.PRACTICE } as ArrayList<Example>
            practiceList.shuffle()
            queue.addAll(practiceList)
            if (queue.isEmpty())
                throw IllegalStateException("Example collection after theory status must contains at least 1 bush_example_app with status Practice")
            return true
        }
        return false
    }

    fun next(): Example {
        return checkNotNull(queue.pollFirst())
    }

    fun upExampleStatus(example: Example) {
        example.upStatus()
    }

}