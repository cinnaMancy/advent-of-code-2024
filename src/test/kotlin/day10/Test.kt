package day10

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        """.trimIndent().lines()

    @Test
    fun part1Example() {
        val hikingMap = HikingMap.parse(input)
        val trailheadScoreCount = hikingMap.trailheadScoreCount()
        assertEquals(36, trailheadScoreCount)
    }
}