package day15

import util.CharacterBoard

class Warehouse(
    val board: CharacterBoard,
    val movements: List<Movement>
) {
    fun gpsSum(): Int = generateSequence(this) { it.performNextMove() }
        //  TODO: Remove me
//        .onEach { println("Moving: ${it.movements.firstOrNull()}");it.board.print();println() }
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
        val nextBoard = PushAttempt(listOf(robot), direction).push(board)
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

class PushAttempt(
    val group: List<CharacterBoard.Coordinate>,
    val vector: CharacterBoard.Coordinate
) {
    //  TODO: Validate if correct
    //   1. if !possible() return board
    //   2. push all non-fully-space subgroups
    //   3. move self
    fun push(board: CharacterBoard): CharacterBoard {
        if (!possible(board)) return board
        val subGroups = groupsAhead(board)
        val spaces = subGroups.filter { nextGroup -> allSpaces(nextGroup, board) }
        val complexSubGroups = subGroups.minus(spaces)
        val afterSubgroupsMoved = complexSubGroups.fold(board) { currentBoard, currentGroup ->
            PushAttempt(currentGroup, vector).push(currentBoard)
        }
        return move(this.group, afterSubgroupsMoved)
    }

    //  TODO: Validate if correct
    //   1. select the front of each swap and map it to a (second level) list of tiles present that move simultaneously (itself, or itself and a neighbor)
    //   2 if this list contains any '#', return false
    //   3 if this list is contains only '.', return true
    //   4 take all other elements than '#' and '.' and map if they all possible()
    private fun possible(board: CharacterBoard): Boolean {
        val subGroups = groupsAhead(board)
        val walls = subGroups.filter { subGroup -> anyWalls(subGroup, board) }
        val spaces = subGroups.filter { subGroup -> allSpaces(subGroup, board) }
        val pushables = subGroups.minus(walls).minus(spaces)
        return when {
            walls.isNotEmpty() -> false
            pushables.isEmpty() -> true
            else -> pushables.all { PushAttempt(it, vector).possible(board) }
        }
    }

    private fun allSpaces(coordinates: List<CharacterBoard.Coordinate>, board: CharacterBoard): Boolean =
        coordinates.all { coordinate -> board[coordinate]?.content == '.' }

    private fun anyWalls(coordinates: List<CharacterBoard.Coordinate>, board: CharacterBoard): Boolean =
        coordinates.any { coordinate -> board[coordinate]?.content == '#' }

    private fun move(group: List<CharacterBoard.Coordinate>, board: CharacterBoard): CharacterBoard =
        group.fold(board) { currentBoard, cell ->
            currentBoard.swap(cell, cell + vector)
        }

    private fun groupsAhead(
        board: CharacterBoard
    ): List<List<CharacterBoard.Coordinate>> =
        group.map { it + vector }
            .map {
                //  TODO: Separate out
                when (board[it]?.content) {
                    '.', '#', '@', 'O' -> listOf(it)
                    '[' -> listOf(it + Movement.RIGHT.vector, it)
                    ']' -> listOf(it + Movement.LEFT.vector, it)
                    else -> throw IllegalArgumentException("Unknown content '${board[it]?.content}'!")
                }
            }
            .distinctBy { it.toSet() }
            //  TODO: Separate out - current group is not picked up
            .filter { it.none { newGroupElement -> group.any { groupElement -> groupElement == newGroupElement } } }
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