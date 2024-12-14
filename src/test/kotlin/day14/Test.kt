package day14

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val bathroom = Bathroom.parse(input, Pair(11, 7))
        val safetyFactorAfter = bathroom.safetyFactorAfter(100)
        assertEquals(12, safetyFactorAfter)
    }
}
