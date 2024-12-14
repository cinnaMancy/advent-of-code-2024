package day14

import util.readTextLines


fun main() {
    val input = readTextLines("/day14.txt")
    val bathroom = Bathroom.parse(input, Pair(101, 103))
    println("Part 1: The security factor after 100 seconds is ${bathroom.safetyFactorAfter(100)}.")
    println("Part 2: Searching for tree...")
    println("Part 2: Tree found on iteration ${bathroom.findFirstChristmasTree()}!")
}