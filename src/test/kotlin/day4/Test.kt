package day4

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent().lines().map { it.toCharArray() }.toTypedArray()

    @Test
    fun part1Example() {
        val board = Board(input)
        val phrase = "XMAS"
        val occurrences = WordSearch(board).count(phrase)
        assertEquals(18, occurrences)
    }

    @Test
    fun part2Example() {
        val board = Board(input)
        val phrase = "MAS"
        val occurrences = WordSearch(board).crossCount(phrase)
        assertEquals(9, occurrences)
    }
}