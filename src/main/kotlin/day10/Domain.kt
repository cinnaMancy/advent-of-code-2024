package day10

import util.CharacterBoard

class HikingMap(
    val heights: CharacterBoard
) {
    fun trailheadsCount(): Int = heights.allTiles.sumOf(::trailheadScore)

//    private fun trailheadScore(tile: CharacterBoard.Tile): Int = generateSequence(tile) { currentTile ->
//        heights.directlyAdjacent(currentTile.coords).any { nextTile ->
//            nextTile.content.digitToInt() == currentTile.content.digitToInt() + 1
//        }
//    }

    private fun trailheadScore(tile: CharacterBoard.Tile): Int = TODO()

    private fun searchTrails(tile: CharacterBoard.Tile, Int): List<List<CharacterBoard.Tile>> = TODO()
}
