package util

data class Coordinate(
    val x: Int,
    val y: Int
) {
    operator fun times(other: Int): Coordinate = Coordinate(x * other, y * other)

    operator fun plus(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)

    operator fun minus(other: Coordinate): Coordinate = Coordinate(x - other.x, y - other.y)
}