package day2

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    private val input = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()

    @Test
    fun part1Example() {
        val reportBatch = ReportBatch.parse(input)
        val safeReports = reportBatch.safeReports()
        assertEquals(2, safeReports)
    }

    @Test
    fun part2Example() {
        val reportBatch = ReportBatch.parse(input)
        val safeReportsToleratingOne = reportBatch.safeReportsToleratingOne()
        assertEquals(4, safeReportsToleratingOne)
    }
}