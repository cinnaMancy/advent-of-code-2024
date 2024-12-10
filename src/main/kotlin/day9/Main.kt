package day9

import util.readText

fun main() {
    val input = readText("/day9.txt")
    val fileSystem = FileSystem.parse(input)
    println("Part 1: The checksum after piecewise fragmentation ${fileSystem.checksumPiecewise()}.")
    println("Part 2: The checksum after whole fragmentation is ${fileSystem.checksumWhole()}.")
}