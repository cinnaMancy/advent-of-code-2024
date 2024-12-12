package day12

import util.CharacterBoard

class Garden(
    val board: CharacterBoard
) {
    fun totalFencingCost() = regions().sumOf(::fencingCost)
    fun totalBulkFencingCost(): Int = regions().sumOf(::bulkFencingCost)

    private fun regions(): List<List<CharacterBoard.Tile>> =
        generateSequence(Pair(listOf<List<CharacterBoard.Tile>>(), board.allTiles)) { (regions, remaining) ->
            val firstOfNextRegion = remaining.firstOrNull() ?: return@generateSequence null
            val nextRegion = fillRegion(firstOfNextRegion)
            return@generateSequence Pair(regions.plus(element = nextRegion), remaining.minus(nextRegion))
        }.last().first

    private fun fillRegion(tile: CharacterBoard.Tile): List<CharacterBoard.Tile> =
        generateSequence(listOf(tile)) { tiles ->
            val next = tiles.flatMap { board.directlyAdjacent(it.coords) }
                .distinct()
                .filter { !tiles.contains(it) }
                .filter { it.content == tiles[0].content }
            return@generateSequence if (next.isEmpty()) null
            else tiles.plus(next)
        }.last()

    private fun fencingCost(region: List<CharacterBoard.Tile>) = area(region) * perimeter(region)

    private fun bulkFencingCost(region: List<CharacterBoard.Tile>) = area(region) * sides(region)

    private fun area(region: List<CharacterBoard.Tile>): Int = region.size

    private fun perimeter(region: List<CharacterBoard.Tile>): Int = groupedAdjacent(region).sumOf { 4 - it.size }

    private fun sides(region: List<CharacterBoard.Tile>): Int = TODO()

    private fun groupedAdjacent(region: List<CharacterBoard.Tile>) =
        region.map { tile -> board.directlyAdjacent(tile.coords).filter { adjacent -> region.contains(adjacent) } }

    companion object {
        fun parse(lines: List<String>): Garden = Garden(CharacterBoard.parse(lines))
    }
}