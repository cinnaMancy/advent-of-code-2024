package day9

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """2333133121414131402"""

    @Test
    fun part1Example() {
        val calibrations = FileSystem.parse(input)
        val checksumPiecewise = calibrations.checksumPiecewise()
        assertEquals(1928, checksumPiecewise)
    }

    @Test
    fun part2Example() {
        val calibrations = FileSystem.parse(input)
        val checksumWhole = calibrations.checksumWhole()
        assertEquals(2858, checksumWhole)
    }
}