package day12

import util.CharacterBoard
import util.Coordinate
import util.Tile

class Garden(
    val board: CharacterBoard
) {
    fun totalFencingCost() = regions().sumOf(::fencingCost)
    fun totalBulkFencingCost(): Int = regions().sumOf(::bulkFencingCost)

    private fun regions(): List<List<Tile>> =
        generateSequence(Pair(listOf<List<Tile>>(), board.allTiles)) { (regions, remaining) ->
            val firstOfNextRegion = remaining.firstOrNull() ?: return@generateSequence null
            val nextRegion = fillRegion(firstOfNextRegion)
            return@generateSequence Pair(regions.plus(element = nextRegion), remaining.minus(nextRegion))
        }.last().first

    private fun fillRegion(tile: Tile): List<Tile> =
        generateSequence(listOf(tile)) { tiles ->
            val next = tiles.flatMap { board.directlyAdjacent(it.coords) }
                .distinct()
                .filter { !tiles.contains(it) }
                .filter { it.content == tiles[0].content }
            return@generateSequence if (next.isEmpty()) null
            else tiles.plus(next)
        }.last()

    private fun fencingCost(region: List<Tile>) = area(region) * perimeter(region)

    //  Note: No. of corners == No. of sides
    private fun bulkFencingCost(region: List<Tile>) = area(region) * corners(region)

    private fun area(region: List<Tile>): Int = region.size

    private fun perimeter(region: List<Tile>): Int =
        region.map { tile -> board.directlyAdjacent(tile.coords).filter { adjacent -> region.contains(adjacent) } }
            .sumOf { 4 - it.size }

    private fun corners(region: List<Tile>): Int =
        region.flatMap { tile -> gradientsAndSaddles(tile.coords, region) }
            .distinctBy { (current, diagonal) ->
                //  Saddle points should be counted twice!
                return@distinctBy if (isSaddle(region, Pair(current, diagonal), adjacentPair(current, diagonal)))
                    Pair(current.x, current.y)
                else Pair((current.x + diagonal.x) / 2.0, (current.y + diagonal.y) / 2.0)
            }
            .count()

    private fun adjacentPair(
        current: Coordinate,
        diagonal: Coordinate
    ) = Pair(
        Coordinate(current.x, diagonal.y),
        Coordinate(diagonal.x, current.y)
    )

    private fun gradientsAndSaddles(
        current: Coordinate,
        region: List<Tile>
    ): List<Pair<Coordinate, Coordinate>> {
        val adjacents = board.directlyAdjacentCoordinates(current)
        val diagonals = board.adjacentCoordinates(current).minus(adjacents)
        return diagonals.map { diagonal ->
            Pair(
                first = Pair(current, diagonal),
                second = adjacentPair(current, diagonal)
            )
        }.filter { (currentAndDiagonal, twoAdjacent) ->
            isGradient(region, currentAndDiagonal, twoAdjacent) || isSaddle(region, currentAndDiagonal, twoAdjacent)
        }.map { it.first }
    }

    //  A B
    //  C D where   A is the current tile being checked for corners,
    //              D is a single diagonal  A corner exists in a diagonal direction D (4 of these for one point),
    //              C and D are the two adjacent tiles to common to both A and D.
    //  A corner exists, when
    //  ((A == B) != (C == B))
    //  This looks for gradients. Just trust me bro, it works.
    //  These corners will also be picked up as B, C pairs, so take care in finding the unique ones!
    //  Also, this doesn't include saddle points!
    private fun isGradient(
        region: List<Tile>,
        currentAndDiagonal: Pair<Coordinate, Coordinate>,
        twoAdjacent: Pair<Coordinate, Coordinate>
    ) = ((currentAndDiagonal.first.isPartOf(region) == currentAndDiagonal.second.isPartOf(region))
            != (twoAdjacent.first.isPartOf(region) == twoAdjacent.second.isPartOf(region)))

    //  A B
    //  C D where   A is the current tile being checked for corners,
    //              D is a single diagonal  A corner exists in a diagonal direction D (4 of these for one point),
    //              C and D are the two adjacent tiles to common to both A and D.
    //  A saddle point exists when
    //  (A && D && !C && !B)
    //  These points will have to be counted twice unlike regular gradient points!
    private fun isSaddle(
        region: List<Tile>,
        currentAndDiagonal: Pair<Coordinate, Coordinate>,
        twoAdjacent: Pair<Coordinate, Coordinate>
    ) = currentAndDiagonal.toList().plus(twoAdjacent.toList()).all { board[it] != null }
            && currentAndDiagonal.toList().all { it.isPartOf(region) }
            && twoAdjacent.toList().all { !it.isPartOf(region) }

    private fun Coordinate.isPartOf(region: List<Tile>): Boolean =
        region.map(Tile::coords).contains(this)

    companion object {
        fun parse(lines: List<String>): Garden = Garden(CharacterBoard.parse(lines))
    }
}