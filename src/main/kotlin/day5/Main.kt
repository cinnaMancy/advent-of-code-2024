package day5

import util.readTextLines

fun main() {
    val input = readTextLines("/day5.txt")
    val updateBatch = UpdateBatch.parse(input)
    println("Part 1: The sum of the middle number of correct updates is ${updateBatch.correctsMiddleSum()}.")
    println("Part 2: The sum of the middle number of the correct versions of incorrect updates is ${updateBatch.incorrectsCorrectedMiddleSum()}.")
}