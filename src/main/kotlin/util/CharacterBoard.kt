package util

class CharacterBoard(
    private val tiles: List<List<Tile>>
) {
    val allTiles get(): Collection<Tile> = tiles.flatten()

    val dimensions get(): Pair<Int, Int> = Pair(tiles[0].size, tiles.size)

    operator fun get(coords: Coordinate): Tile? =
        if (coords.y in tiles.indices && coords.x in tiles[0].indices) tiles[coords.y][coords.x]
        else null

    fun adjacentCoordinates(coords: Coordinate): Collection<Coordinate> = (-1..1).flatMap { dx ->
        (-1..1).filter { dy -> !(dx == 0 && dy == 0) }.map { dy -> coords + Coordinate(dx, dy) }
    }

    fun adjacent(coords: Coordinate): Collection<Tile> =
        adjacentCoordinates(coords).mapNotNull(::get)

    fun directlyAdjacentCoordinates(coords: Coordinate): Collection<Coordinate> =
        adjacentCoordinates(coords).filter { (it.x - coords.x) * (it.y - coords.y) == 0 }

    fun directlyAdjacent(coords: Coordinate): Collection<Tile> =
        directlyAdjacentCoordinates(coords).mapNotNull(::get)

    fun swap(one: Coordinate, other: Coordinate): CharacterBoard {
        val oneTile = get(one)
        val otherTile = get(other)
        if (listOf(oneTile, otherTile).contains(null)) throw IllegalArgumentException()
        val swappedTiles = tiles.map { row ->
            row.map { tile ->
                when (tile) {
                    oneTile -> Tile(oneTile.coords, otherTile!!.content)
                    otherTile -> Tile(otherTile.coords, oneTile!!.content)
                    else -> tile
                }
            }
        }
        return CharacterBoard(swappedTiles)
    }

    fun print(): Unit {
        tiles.reversed().forEach { row -> row.forEach { tile -> print(tile.content) }.also { println() } }
    }

    companion object {
        fun parse(lines: List<String>): CharacterBoard = CharacterBoard(
            lines.reversed().mapIndexed { iY, y -> y.mapIndexed { iX, x -> Tile(Coordinate(iX, iY), x) } }
        )
    }

}
