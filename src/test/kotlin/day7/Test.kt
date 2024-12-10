package day7

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val calibrations = Calibrations.parse(input)
        val possibleSum = calibrations.possibleWithTwoOperatorSum()
        assertEquals(3749, possibleSum)
    }

    @Test
    fun part2Example() {
        val calibrations = Calibrations.parse(input)
        val possibleSum = calibrations.possibleWithThreeOperatorSum()
        assertEquals(11387, possibleSum)
    }
}