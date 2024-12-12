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

    //  Note: No. of corners == No. of sides
    private fun bulkFencingCost(region: List<CharacterBoard.Tile>) = area(region) * corners(region)

    private fun area(region: List<CharacterBoard.Tile>): Int = region.size

    private fun perimeter(region: List<CharacterBoard.Tile>): Int = groupedAdjacent(region).sumOf { 4 - it.size }

    private fun groupedAdjacent(region: List<CharacterBoard.Tile>) =
        region.map { tile -> board.directlyAdjacent(tile.coords).filter { adjacent -> region.contains(adjacent) } }

    private fun corners(region: List<CharacterBoard.Tile>): Int =
        region.flatMap { tile -> corners(tile.coords, region) }
            .distinctBy { (current, diagonal) ->
                Pair(current.x + diagonal.x, current.y + diagonal.y)
            }
            .count()

    //  C D
    //  A B
    //  where   A is the current tile being checked for corners
    //          D is a single diagonal  A corner exists in a diagonal direction D (4 of these for one point), where
    //          C and D are the two adjacent tiles to common to both A and D.
    //          The function returns pairs of A to D, a maximum of 4 per call.
    //          A corner exists, when (A == B) != (C == B). I couldn't explain exactly why, but trust me bro.
    //          These corners may also be picked up as B, C pairs!
    private fun corners(
        currentCoords: CharacterBoard.Coordinate,
        region: List<CharacterBoard.Tile>
    ): List<Pair<CharacterBoard.Coordinate, CharacterBoard.Coordinate>> {
        val adjacents = board.directlyAdjacentCoordinates(currentCoords)
        val diagonals = board.adjacentCoordinates(currentCoords).minus(adjacents)
        return diagonals.map { diagonal ->
            Pair(
                first = Pair(currentCoords, diagonal),
                second = adjacents.filter { board.directlyAdjacentCoordinates(diagonal).contains(it) })
        }.filter { (currentAndDiagonal, twoAdjacent) ->
            val (a, d) = currentAndDiagonal
            val (b, c) = twoAdjacent
            (a.isPartOf(region) == d.isPartOf(region)) != (b.isPartOf(region) == c.isPartOf(region))
        }.map { it.first }
    }

    private fun CharacterBoard.Coordinate.isPartOf(region: List<CharacterBoard.Tile>): Boolean =
        region.any { this == it.coords }

    companion object {
        fun parse(lines: List<String>): Garden = Garden(CharacterBoard.parse(lines))
    }
}