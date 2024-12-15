package day15

import util.CharacterBoard

class Warehouse(
    val board: CharacterBoard,
    val movements: List<Movement>
) {
    fun gpsSum(): Int = generateSequence(this) { it.performNextMove() }
        .onEach { println("Moving: ${it.movements.firstOrNull()}");it.board.print();println() }
        .last()
        .board
        .allTiles
        .filter { listOf('O', '[').contains(it.content) }
        .sumOf { gpsScore(it) }

    private fun gpsScore(tile: CharacterBoard.Tile): Int =
        tile.coords.x + ((board.dimensions.second - tile.coords.y - 1) * 100)

    private fun performNextMove(): Warehouse? {
        val direction = movements.firstOrNull()?.vector ?: return null
        val robot = board.allTiles.first { it.content == '@' }.coords
        val nextBoard = SwapAttempt(robot, direction).perform(board)
        return Warehouse(nextBoard, movements.drop(1))
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

        fun parseBig(lines: List<String>): Warehouse {
            val lineBreak = lines.indexOfFirst { it == "" }
            val boardLines = lines.subList(0, lineBreak)
            val restLines = lines.subList(lineBreak, lines.size)
            val doubleBoardLines = boardLines.map { line ->
                line.map { char ->
                    when (char) {
                        'O' -> "[]"
                        '@' -> "@."
                        else -> "$char$char"
                    }
                }.joinToString("")
            }
            return parse(doubleBoardLines.plus(restLines))
        }
    }
}

class SwapAttempt(
    val base: CharacterBoard.Coordinate,
    val vector: CharacterBoard.Coordinate
) {
    private val target: CharacterBoard.Coordinate = base + vector

    fun perform(board: CharacterBoard): CharacterBoard = if (possibleWhole(board)) swap(board) else board

    private fun swap(board: CharacterBoard): CharacterBoard {
        val actedBoard =
            if (possibleInPlace(board)) board
            else children(board).fold(board) { childBoard, childSwap ->
                childSwap.swap(childBoard)
            }
        return actedBoard.swap(base, target)
    }

    private fun possibleWhole(board: CharacterBoard): Boolean = when (board[target]!!.content) {
        '#' -> false
        '.' -> true
        else -> children(board).all { it.possibleWhole(board) }
    }

    private fun possibleInPlace(board: CharacterBoard): Boolean = board[target]!!.content == '.'

    private fun children(board: CharacterBoard): List<SwapAttempt> = when (board[base]!!.content) {
        '.', '#' -> listOf()
        '@', 'O' -> listOf(ahead())
        '[' -> listOf(ahead(), ahead(CharacterBoard.Coordinate(1, 0)))
        ']' -> listOf(ahead(), ahead(CharacterBoard.Coordinate(-1, 0)))
        else -> throw IllegalArgumentException("Unknown content '${board[base]!!.content}'!")
    }

    private fun ahead(displacement: CharacterBoard.Coordinate = CharacterBoard.Coordinate(0, 0)): SwapAttempt =
        SwapAttempt(target + displacement, vector)
}

enum class Movement(val vector: CharacterBoard.Coordinate) {
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