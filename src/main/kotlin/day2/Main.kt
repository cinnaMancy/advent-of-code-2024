package day2

import util.readTextLines

fun main() {
    val input = readTextLines("/day2.txt")
    val reportBatch = ReportBatch.parse(input)
    println("Part 1: The number of safe reports is ${reportBatch.safeReports()}.")
    println("Part 2: The number of safe reports tolerating a one level point is ${reportBatch.safeReportsToleratingOne()}.")
}