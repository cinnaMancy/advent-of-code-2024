package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val pairBatch = NumberPairBatch.parse(input)
        val sumDistance = pairBatch.sumDistance()
        assertEquals(11, sumDistance)
    }

    @Test
    fun part2Example() {
        val pairBatch = NumberPairBatch.parse(input)
        val similarityScore = pairBatch.similarityScore()
        assertEquals(31, similarityScore)
    }
}