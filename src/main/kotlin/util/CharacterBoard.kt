package util

class CharacterBoard(
    private val tiles: List<List<Tile>>
) {
    val allTiles get(): Collection<Tile> = tiles.flatten()

    operator fun get(coords: Coordinate): Tile? =
        if (coords.y in tiles.indices && coords.x in tiles[0].indices) tiles[coords.y][coords.x]
        else null

    fun adjacent(coords: Coordinate): Collection<Tile> = (-1..1).flatMap { dx ->
        (-1..1).filter { dy -> !(dx == 0 && dy == 0) }.map { dy -> this[coords + Coordinate(dx, dy)] }
    }.filterNotNull()

    fun directlyAdjacent(coords: Coordinate): Collection<Tile> =
        adjacent(coords).filter { (it.coords.x - coords.x) * (it.coords.y - coords.y) == 0 }

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
