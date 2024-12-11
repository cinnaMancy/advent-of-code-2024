package day11

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = "125 17"

    @Test
    fun part1Example() {
        val stoneLine = StoneLine.parse(input)
        val stonesCountAfter25Evolutions = stoneLine.stonesCountAfter25Evolutions()
        assertEquals(55312, stonesCountAfter25Evolutions)
    }
}
