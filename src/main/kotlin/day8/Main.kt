package day8

import util.readTextLines

fun main() {
    val input = readTextLines("/day8.txt")
    val antinodes = Antinodes.parse(input)
    println("Part 1: The number of antinodes with single antinodes is ${antinodes.singleLocationsCount()}.")
    println("Part 2: The number of antinodes with line antinodes is ${antinodes.lineLocationsCount()}.")
}