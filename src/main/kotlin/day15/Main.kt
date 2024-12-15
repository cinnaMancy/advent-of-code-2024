package day15

import util.readTextLines


fun main() {
    val input = readTextLines("/day15.txt")
    val warehouse = Warehouse.parse(input)
    println("Part 1: The sum of all boxes' GPS coordinates is ${warehouse.gpsSum()}.")
}