package day7

import util.readTextLines

fun main() {
    val input = readTextLines("/day7.txt")
    val calibrations = Calibrations.parse(input)
    println("Part 1: The sum possible with two operators is ${calibrations.possibleWithTwoOperatorSum()}.")
    println("Part 2: The sum possible with three operators is ${calibrations.possibleWithThreeOperatorSum()}.")
}