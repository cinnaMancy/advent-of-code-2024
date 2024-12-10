package day3

import util.readText

fun main() {
    val input = readText("/day3.txt")
    val program = Program(input)
    println("Part 1: The sum of multiplications is ${program.multiplicationsSum()}.")
    println("Part 2: The sum of multiplication after enabling text is ${program.enabledMultiplicationsSum()}.")
}