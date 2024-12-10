package day1

import util.readTextLines

fun main() {
    val input = readTextLines("/day1.txt")
    val pairBatch = NumberPairBatch.parse(input)
    println("Part 1: The sum of distances is ${pairBatch.sumDistance()}.")
    println("Part 1: The similarity score is ${pairBatch.similarityScore()}.")
}