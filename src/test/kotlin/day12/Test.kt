package day12

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input1 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent().lines()

    private val input2 = """
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val garden = Garden.parse(input1)
        val totalFencingCost = garden.totalFencingCost()
        assertEquals(1930, totalFencingCost)
    }

    @Test
    fun part2Example() {
        val garden = Garden.parse(input1)
        val totalBulkFencingCost = garden.totalBulkFencingCost()
        assertEquals(368, totalBulkFencingCost)
    }
}
