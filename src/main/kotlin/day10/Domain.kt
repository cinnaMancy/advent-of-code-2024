package day10

import util.CharacterBoard

class HikingMap(
    private val heights: CharacterBoard
) {
    fun trailheadScoreCount(): Int = heights.allTiles.map(::trails)
        .sumOf(List<List<CharacterBoard.Tile>>::size)

    private fun trails(tile: CharacterBoard.Tile): List<List<CharacterBoard.Tile>> = trails(listOf(tile), 0)
        .distinctBy { it.last() }

    private fun trails(path: List<CharacterBoard.Tile>, height: Int): List<List<CharacterBoard.Tile>> =
        if (!path.last().content.isDigit() || height != path.last().content.digitToInt()) emptyList()
        else if (height == 9) listOf(path)
        else heights.directlyAdjacent(path.last().coords).flatMap { trails(path.plus(it), height + 1) }

    companion object {
        fun parse(lines: List<String>): HikingMap = HikingMap(CharacterBoard.parse(lines))
    }
}
