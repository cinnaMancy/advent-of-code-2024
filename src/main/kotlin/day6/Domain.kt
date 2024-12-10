package day6

class Grid(
    val tiles: List<List<Tile>>
) {
    fun traversedTilesCount(): Int = traversedTilesSequence()
        .map { (tile, _) -> tile }
        .toList().distinct().count()

    fun infiniteObstructionsCount(): Int = traversedTilesSequence()
        .map { (traversedTile, _) -> traversedTile }
        .filter { !it.isDirection }
        .distinct()
        .map { traversedTile -> replace(Tile('O', traversedTile.coords)) }
        .filter(Grid::traversesInfinitely)
        .count()

    private fun traversedTilesSequence(): Sequence<Pair<Tile, Direction>> =
        generateSequence(Pair(startingPosition(), Direction.N)) { (tile, direction) ->
            if (tile.move(direction) == null) null
            else if (tile.move(direction)!!.isObstruction) Pair(tile, direction.rotateRight())
            else Pair(tile.move(direction)!!, direction)
        }

    private fun traversesInfinitely(): Boolean {
        val previousSteps: MutableList<Pair<Tile, Direction>> = mutableListOf()
        traversedTilesSequence().forEach { currentStep ->
            if (previousSteps.contains(currentStep)) return true
            previousSteps.add(currentStep)
        }
        return false
    }

    private fun startingPosition(): Tile =
        tiles.flatten().first(Tile::isDirection)

    private fun get(coords: Pair<Int, Int>): Tile? =
        if (coords.first in tiles[0].indices && coords.second in tiles[1].indices) tiles[coords.second][coords.first]
        else null

    private fun Tile.move(direction: Direction): Tile? = get(this.coords + direction)

    private fun replace(replacement: Tile): Grid = Grid(
        tiles.map { row ->
            row.map { tile ->
                if (tile.coords == replacement.coords) replacement
                else tile
            }
        }
    )

    companion object {
        fun parse(text: List<String>): Grid =
            Grid(text.reversed().mapIndexed { iY, y -> y.mapIndexed { iX, x -> Tile(x, Pair(iX, iY)) } })
    }
}

data class Tile(
    val content: Char,
    val coords: Pair<Int, Int>
) {
    val isObstruction get() = listOf('#', 'O').contains(content)
    val isDirection get() = Direction.entries.map(Direction::sign).contains(content)
}

enum class Direction(
    val vector: Pair<Int, Int>,
    val sign: Char
) {
    N(Pair(0, 1), '^'),
    E(Pair(1, 0), '>'),
    S(Pair(0, -1), 'v'),
    W(Pair(-1, 0), '<');

    fun rotateRight(): Direction = entries[(this.ordinal + 1) % entries.size]
}

private operator fun Pair<Int, Int>.plus(other: Direction): Pair<Int, Int> =
    Pair(this.first + other.vector.first, this.second + other.vector.second)