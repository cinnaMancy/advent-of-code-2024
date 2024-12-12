package day12

import util.readTextLines

fun main() {
    val input = readTextLines("/day12.txt")
    val garden = Garden.parse(input)
    println("Part 1: The total fencing cost for the garden is ${garden.totalFencingCost()}.")
    println("Part 2: The total fencing cost for the garden when counting sides is ${garden.totalBulkFencingCost()}.")
}
