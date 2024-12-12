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
        region.flatMap { tile -> gradients(tile.coords, region) }
            .distinctBy { (current, diagonal) ->
                //  Saddle points should be counted both ways!
                return@distinctBy if (isSaddlePoint(
                        region,
                        current,
                        diagonal,
                        CharacterBoard.Coordinate(current.x, diagonal.y),
                        CharacterBoard.Coordinate(diagonal.x, current.y)
                    )
                ) Pair(current.x, current.y)
                else Pair((current.x + diagonal.x) / 2.0, (current.y + diagonal.y) / 2.0)
            }
            .count()

    //  C D
    //  A B where   A is the current tile being checked for corners,
    //              D is a single diagonal  A corner exists in a diagonal direction D (4 of these for one point),
    //              C and D are the two adjacent tiles to common to both A and D.
    //  The function returns pairs of A to D, a maximum of 4 per call.
    //  A corner exists, when
    //  ((A == B) != (C == B)) || (A && B && !C && !D)
    //  The first part looks for gradients. The part after the || is for saddle points.
    //  These corners may also be picked up as B, C pairs! Also care for saddle points!
    private fun gradients(
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
            val (current, diagonal) = currentAndDiagonal
            val (adjacent1, adjacent2) = twoAdjacent
            isGradient(region, current, diagonal, adjacent1, adjacent2)
                    || isSaddlePoint(region, current, diagonal, adjacent1, adjacent2)
        }.map { it.first }
    }

    private fun isGradient(
        region: List<CharacterBoard.Tile>,
        current: CharacterBoard.Coordinate,
        diagonal: CharacterBoard.Coordinate,
        adjacent1: CharacterBoard.Coordinate,
        adjacent2: CharacterBoard.Coordinate
    ) = ((current.isPartOf(region) == diagonal.isPartOf(region))
            != (adjacent1.isPartOf(region) == adjacent2.isPartOf(region)))

    private fun isSaddlePoint(
        region: List<CharacterBoard.Tile>,
        current: CharacterBoard.Coordinate,
        diagonal: CharacterBoard.Coordinate,
        adjacent1: CharacterBoard.Coordinate,
        adjacent2: CharacterBoard.Coordinate
    ) = listOf(current, diagonal, adjacent1, adjacent2).all { board[it] != null }
            && (current.isPartOf(region) && diagonal.isPartOf(region)
            && !adjacent1.isPartOf(region) && !adjacent2.isPartOf(region))

    private fun CharacterBoard.Coordinate.isPartOf(region: List<CharacterBoard.Tile>): Boolean =
        region.map(CharacterBoard.Tile::coords).contains(this)

    companion object {
        fun parse(lines: List<String>): Garden = Garden(CharacterBoard.parse(lines))
    }
}