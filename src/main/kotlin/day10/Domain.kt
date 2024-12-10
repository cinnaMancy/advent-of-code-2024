package day10

import util.CharacterBoard

class HikingMap(
    val heights: CharacterBoard
) {
    fun trailheadScoreCount(): Int = heights.allTiles.sumOf(::trailheadScore)

    private fun trailheadScore(tile: CharacterBoard.Tile): Int = routesToTop(tile, 0)

    private fun routesToTop(tile: CharacterBoard.Tile, height: Int): Int =
        if (tile.content.digitToInt() != height) 0
        else if (tile.content.digitToInt() == 9) 1
        else heights.directlyAdjacent(tile.coords).sumOf { routesToTop(it, height + 1) }

    companion object {
        fun parse(lines: List<String>): HikingMap = HikingMap(CharacterBoard.parse(lines))
    }
}
