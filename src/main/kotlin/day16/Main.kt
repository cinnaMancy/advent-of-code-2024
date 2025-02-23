package day16

import util.readTextLines

fun main() {
    val input = readTextLines("/day16.txt")
    val path = Path.parse(input)
    println("Part 1: The score of the cheapest path is ${path.minScore()}.")
}