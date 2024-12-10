package day10

import util.readTextLines

fun main() {
    val input = readTextLines("/day10.txt")
    val hikingMap = HikingMap.parse(input)
    println("Part 1: The total sum of trailscores is ${hikingMap.trailheadScoreCount()}.")
    println("Part 2: The total sum of ratings is ${hikingMap.ratingScoreCount()}.")
}
