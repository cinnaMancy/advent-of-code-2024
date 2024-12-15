package day15

import util.readTextLines


fun main() {
    val input = readTextLines("/day15.txt")
    val warehouse = Warehouse.parse(input)
    val bigWarehouse = Warehouse.parseBig(input)
    println("Part 1: The sum of all boxes' GPS coordinates is the regular warehouse is ${warehouse.gpsSum()}.")
    println("Part 1: The sum of all boxes' GPS coordinates in the big warehouse is ${bigWarehouse.gpsSum()}.")
}