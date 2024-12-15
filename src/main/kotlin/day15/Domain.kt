package day15

import util.CharacterBoard

class Warehouse(
    val board: CharacterBoard,
    val movements: List<Movement>
) {
    fun gpsSum(): Int = generateSequence(this) { it.performNextMove() }
        .last()
        .board
        .allTiles
        .filter { it.content == 'O' }
        .sumOf { gpsScore(it) }

    private fun gpsScore(tile: CharacterBoard.Tile): Int =
        tile.coords.x + ((board.dimensions.second - tile.coords.y - 1) * 100)

    private fun performNextMove(): Warehouse? {
        val direction = movements.firstOrNull()?.direction ?: return null
        val robot = board.allTiles.first { it.content == '@' }.coords
        val nextBoard = push(robot, direction)
        return Warehouse(nextBoard, movements.drop(1))
    }

    private fun push(
        currentPosition: CharacterBoard.Coordinate,
        direction: CharacterBoard.Coordinate
    ): CharacterBoard {
        val nextPosition = currentPosition + direction
        val nextTile = board[nextPosition]!!
        val actedBoard =
            if (!listOf('#', '.').contains(nextTile.content)) push(nextPosition, direction)
            else board
        val actedTile = actedBoard[nextPosition]!!
        return when (actedTile.content) {
            '.' -> actedBoard.swap(currentPosition, nextPosition)
            else -> actedBoard
        }
    }

    companion object {
        fun parse(lines: List<String>): Warehouse {
            val lineBreak = lines.indexOfFirst { it == "" }
            val boardLines = lines.subList(0, lineBreak)
            val movementText = lines.subList(lineBreak + 1, lines.size).joinToString("")
            return Warehouse(
                board = CharacterBoard.parse(boardLines),
                movements = movementText.map { Movement.parse(it) }
            )
        }
    }
}

enum class Movement(val direction: CharacterBoard.Coordinate) {
    UP(CharacterBoard.Coordinate(0, 1)),
    DOWN(CharacterBoard.Coordinate(0, -1)),
    LEFT(CharacterBoard.Coordinate(-1, 0)),
    RIGHT(CharacterBoard.Coordinate(1, 0));

    companion object {
        fun parse(char: Char): Movement = when (char) {
            '^' -> UP
            'v' -> DOWN
            '<' -> LEFT
            '>' -> RIGHT
            else -> throw IllegalArgumentException("Can't parse movement '$char'!")
        }
    }
}