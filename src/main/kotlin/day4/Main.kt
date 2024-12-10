package day4

import util.readTextArray

fun main() {
    val input = readTextArray("/day4.txt")
    val board = Board(input)
    val search = WordSearch(board)
    println("Part 1: The phrase XMAS occurs ${search.count("XMAS")} times on the board.")
    println("Part 2: The phrase MAS in crossed fashion occurs ${search.crossCount("MAS")} times on the board.")
}