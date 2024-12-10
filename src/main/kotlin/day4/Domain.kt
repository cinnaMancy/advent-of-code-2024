package day4

class WordSearch(
    private val board: Board
) {
    fun count(phrase: String): Int =
        board.indices.sumOf { index ->
            Direction.entries.sumOf { direction ->
                if (matches(index, direction, phrase)) 1 as Int else 0
            }
        }

    fun crossCount(phrase: String): Int =
        board.indices.sumOf { index ->
            Direction.nonOrthogonal.sumOf { direction ->
                if (matchesCross(index, direction, phrase)) 1 as Int else 0
            } / 2
        }

    private fun matches(index: Cell, direction: Direction, phrase: String): Boolean =
        phrase.isEmpty() || (board.get(index)?.equals(phrase.first()) ?: false && matches(
            index + direction.vector,
            direction,
            phrase.drop(1)
        ))

    private fun matchesCross(index: Cell, direction: Direction, phrase: String): Boolean {
        if (phrase.length % 2 == 0) throw IllegalArgumentException("'$phrase' does not have an odd number of letters!")
        val radius = phrase.length / 2
        val startIndex = index + (direction.vector * -radius)
        val crossDirectionsByStartIndices = direction
            .perpendiculars()
            .associateWith { crossDirection -> index + (crossDirection.vector * -radius) }
        return matches(startIndex, direction, phrase)
                && crossDirectionsByStartIndices.any { (crossDirection, crossStartIndex) ->
            matches(crossStartIndex, crossDirection, phrase)
        }
    }
}

class Board(
    private val board: Array<CharArray>
) {
    val size get(): Pair<IntRange, IntRange> = Pair(board[0].indices, board.indices)

    val indices
        get(): Array<Cell> = this.size.first.flatMap { x -> this.size.second.map { y -> Cell(x, y) } }.toTypedArray()

    fun get(coordinates: Cell): Char? =
        if (coordinates.x in size.first && coordinates.y in size.second) board[coordinates.x][coordinates.y]
        else null
}

class Cell(
    val x: Int,
    val y: Int
)

enum class Direction(
    val vector: Cell
) {
    N(Cell(0, 1)),
    NE(Cell(1, 1)),
    E(Cell(1, 0)),
    SE(Cell(1, -1)),
    S(Cell(0, -1)),
    SW(Cell(-1, -1)),
    W(Cell(-1, 0)),
    NW(Cell(-1, 1));

    fun perpendiculars(): Array<Direction> =
        arrayOf(rotateClockwise(entries.size / 4), rotateClockwise(3 * entries.size / 4))

    fun rotateClockwise(times: Int): Direction = entries[(ordinal + times) % entries.size]

    fun orthogonal(): Boolean = arrayOf(vector.x, vector.y).contains(0)

    companion object {
        val nonOrthogonal: Array<Direction> = entries.filter { !it.orthogonal() }.toTypedArray()
    }
}

operator fun Cell.plus(operand: Cell): Cell =
    Cell(x + operand.x, y + operand.y)

operator fun Cell.times(operand: Int): Cell = Cell(x * operand, y * operand)