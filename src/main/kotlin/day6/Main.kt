package day6

import util.readTextLines

fun main() {
    val input = readTextLines("/day6.txt")
    val grid = Grid.parse(input)
    println("Part 1: The number of traversed tiles is ${grid.traversedTilesCount()}.")
    println("Part 2: The number of single obstacles that would cause an infinite loop is ${grid.infiniteObstructionsCount()}.")
}

