package day16

import util.CharacterBoard
import util.Coordinate

class Path(
    val board: CharacterBoard,
    val head: Coordinate,
    val direction: Coordinate,
    val traversed: Set<Path>
) {
    fun minScore(): Long = findPaths().minOf { it.score() }

    private fun findPaths(): Set<Path> {
        if (board[head]?.content == 'E') return setOf(this)
        val ahead = listOf(Coordinate(1, 0), Coordinate(0, 1), Coordinate(-1, 0), Coordinate(0, -1))
            .minus(direction * -1)
        val nexsts = ahead
            .map { head + it }
            .filter { board[it]?.content != '#' }
            .filter { nextHead -> traversed.none { it.head == nextHead } }
            .map { step(it) }
        if (nexsts.isEmpty()) return emptySet()
        return nexsts.flatMap { it.findPaths() }.toSet()
    }

    private fun score(): Long =
        traversed.plus(this)
            .map { it.direction }
            .zipWithNext()
            .sumOf { (one, other) -> if (one == other) 1L else 1001L }

    private fun step(next: Coordinate): Path = Path(board, next, next - head, traversed.plus(this))

    companion object {
        fun parse(lines: List<String>): Path = start(CharacterBoard.parse(lines))

        private fun start(board: CharacterBoard): Path =
            Path(board, board.allTiles.find { it.content == 'S' }!!.coords, Coordinate(1, 0), emptySet())
    }
}