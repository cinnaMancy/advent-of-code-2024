package day6

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
        """.trimIndent().lines()

    @Test
    fun part1Example() {
        val grid = Grid.parse(input)
        val traversedTilesCount = grid.traversedTilesCount()
        assertEquals(41, traversedTilesCount)
    }

    @Test
    fun part2Example() {
        val grid = Grid.parse(input)
        val infiniteObstructionsCount = grid.infiniteObstructionsCount()
        assertEquals(6, infiniteObstructionsCount)
    }
}