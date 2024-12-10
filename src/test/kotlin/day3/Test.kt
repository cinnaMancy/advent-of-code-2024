package day3

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input1 = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    private val input2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    @Test
    fun part1Example() {
        val program = Program(input1)
        val multiplicationsSum = program.multiplicationsSum()
        assertEquals(161, multiplicationsSum)
    }

    @Test
    fun part2Example() {
        val program = Program(input2)
        val multiplicationsSum = program.enabledMultiplicationsSum()
        assertEquals(48, multiplicationsSum)
    }
}