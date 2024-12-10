package day8

import util.CharacterBoard

class Antinodes(
    val board: CharacterBoard
) {
    fun singleLocationsCount(): Int =
        allFrequencyPairVariations()
            .map { (firstTile, secondTile) -> firstTile.coords + (firstTile.coords - secondTile.coords) }
            .mapNotNull { coords -> board[coords] }
            .distinct()
            .count()

    fun lineLocationsCount(): Int =
        allFrequencyPairVariations()
            .flatMap { (firstTile, secondTile) ->
                generateSequence(firstTile) { board[it.coords + (firstTile.coords - secondTile.coords)] }
            }
            .distinct()
            .count()

    private fun allFrequencyPairVariations(): Sequence<Pair<CharacterBoard.Tile, CharacterBoard.Tile>> =
        board.allTiles
            .asSequence()
            .filter { tile -> tile.content != '.' }
            .groupBy(CharacterBoard.Tile::content)
            .asSequence()
            .flatMap { (_, groupOfTiles) -> allPairVariations(groupOfTiles) }

    private fun <T> allPairVariations(elements: List<T>): List<Pair<T, T>> =
        elements.indices.flatMap { x ->
            elements.indices.filter { y -> x != y }.map { y -> Pair(elements[x], elements[y]) }
        }

    companion object {
        fun parse(lines: List<String>): Antinodes = Antinodes(CharacterBoard.parse(lines))
    }
}

