package day13

import util.readTextLines


fun main() {
    val input = readTextLines("/day13.txt")
    val clawMachines = ClawMachines.parse(input)
    println("Part 1: The fewest number of tokens to win all prizes is ${clawMachines.fewestTokensForAllPrizes()}.")
    println("Part 2: The fewest number of tokens to win all far-away prizes is ${clawMachines.fewestTokensForAllFarAwayPrizes()}.")
}