package day11

import util.readText

fun main() {
    val input = readText("/day11.txt")
    val stoneLine = StoneLine.parse(input)
    println("Part 1: The number of stones after 25 evolutions is ${stoneLine.stonesCountAfter25Evolutions()}.")
    println("Part 1: The number of stones after 75 evolutions is ${stoneLine.stonesCountAfter75Evolutions()}.")
}
