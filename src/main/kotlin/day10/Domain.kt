package day10

import util.CharacterBoard
import util.Tile

class HikingMap(
    private val heights: CharacterBoard
) {
    fun trailheadScoreCount(): Int = allTrails()
        .distinctBy { Pair(it.first(), it.last()) }
        .size

    fun ratingScoreCount(): Int = allTrails()
        .size

    private fun allTrails(): List<List<Tile>> = heights.allTiles
        .flatMap(::trails)
        .filter(List<Tile>::isNotEmpty)

    private fun trails(tile: Tile): List<List<Tile>> = trails(listOf(tile), 0)

    private fun trails(path: List<Tile>, height: Int): List<List<Tile>> =
        if (height != path.last().content.digitToInt()) emptyList()
        else if (height == 9) listOf(path)
        else heights.directlyAdjacent(path.last().coords).flatMap { trails(path.plus(it), height + 1) }

    companion object {
        fun parse(lines: List<String>): HikingMap = HikingMap(CharacterBoard.parse(lines))
    }
}
