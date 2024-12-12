package util

class CharacterBoard(
    private val tiles: List<List<Tile>>
) {
    val allTiles get(): Collection<Tile> = tiles.flatten()

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

    companion object {
        fun parse(lines: List<String>): CharacterBoard = CharacterBoard(lines.reversed().mapIndexed { iY, y ->
            y.mapIndexed { iX, x -> Tile(Coordinate(iX, iY), x) }
        })
    }

    data class Tile(
        val coords: Coordinate,
        val content: Char
    )

    data class Coordinate(
        val x: Int,
        val y: Int
    ) {
        operator fun plus(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)

        operator fun minus(other: Coordinate): Coordinate = Coordinate(x - other.x, y - other.y)
    }
}
