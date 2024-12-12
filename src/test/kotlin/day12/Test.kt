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
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent().lines()

    private val input3 = """
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE
    """.trimIndent().lines()

    private val input4 = """
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
        val garden1 = Garden.parse(input1)
        val garden2 = Garden.parse(input2)
        val garden3 = Garden.parse(input3)
        val garden4 = Garden.parse(input4)
        assertEquals(1206, garden1.totalBulkFencingCost())
        assertEquals(80, garden2.totalBulkFencingCost())
        assertEquals(236, garden3.totalBulkFencingCost())
        assertEquals(368, garden4.totalBulkFencingCost())
    }
}
