package day14

import kotlin.math.ceil

class Bathroom(
    val robots: List<Robot>,
    val dimensions: Pair<Int, Int>
) {
    fun safetyFactorAfter(steps: Int): Int = quadrantCounts(positionsAfter(steps))

    fun findFirstChristmasTree(): Int = generateSequence(0) { it + 1 }
        .map { positionsAfter(it) }
        .withIndex()
        .filter { (_, positions) ->
            positions.any { current -> formsLineToward(Pair(0, 1), 12, current, positions) }
        }
        .onEach { (index, positions) ->
            println("Iteration $index:")
            draw(positions)
        }
        .map { it.index }
        .first()

    private fun quadrantCounts(positions: List<Pair<Int, Int>>): Int =
        positions
            .groupBy(::quadrant)
            .entries
            .filter { it.key != null }
            .map { it.value.size }
            .fold(1) { one, other -> one * other }

    private fun draw(positions: List<Pair<Int, Int>>): Unit {
        (0..<dimensions.first).forEach { x ->
            (0..<dimensions.second).forEach { y ->
                val robotsCount = positions.count { it == Pair(x, y) }
                val tile = if (robotsCount > 0) robotsCount else '.'
                print(tile)
            }
            println()
        }
    }

    private fun formsLineToward(
        direction: Pair<Int, Int>, length: Int, current: Pair<Int, Int>, positions: List<Pair<Int, Int>>
    ): Boolean =
        if (length == 0) true
        else positions.any { positions.contains(current) }
                && formsLineToward(direction, length - 1, current + direction, positions)

    fun quadrant(position: Pair<Int, Int>): Pair<Int, Int>? {
        val xSize = dimensions.first / 2
        val ySize = dimensions.second / 2
        val xSecond = ceil(dimensions.first / 2.0).toInt()
        val ySecond = ceil(dimensions.second / 2.0).toInt()
        val x = when (position.first) {
            in 0..<xSize -> 0
            in xSecond..<xSecond + xSize -> 1
            else -> return null
        }
        val y = when (position.second) {
            in 0..<ySize -> 0
            in ySecond..<ySecond + ySize -> 1
            else -> return null
        }
        return Pair(x, y)
    }

    private fun positionsAfter(steps: Int): List<Pair<Int, Int>> =
        robots.map { positionsAfter(it, steps) }

    private fun positionsAfter(robot: Robot, steps: Int): Pair<Int, Int> =
        clip((robot.position + (robot.velocity * steps)))

    private fun clip(displacement: Pair<Int, Int>): Pair<Int, Int> =
        Pair(clip(displacement.first, dimensions.first), clip(displacement.second, dimensions.second))

    private fun clip(displacement: Int, dimension: Int): Int {
        val scale = (displacement.toDouble() / dimension).toInt()
        val correction =
            if (displacement < 0 && displacement % dimension != 0) (scale * dimension) - dimension
            else scale * dimension
        return displacement - correction
    }

    companion object {
        fun parse(lines: List<String>, dimensions: Pair<Int, Int>): Bathroom =
            Bathroom(lines.map(Robot.Companion::parse), dimensions)
    }
}

class Robot(
    val position: Pair<Int, Int>,
    val velocity: Pair<Int, Int>
) {
    companion object {
        fun parse(line: String): Robot = """p=(.*),(.*) v=(.*),(.*)""".toRegex()
            .matchEntire(line)!!
            .groupValues
            .drop(1)
            .map(String::toInt)
            .let { Robot(position = Pair(it[0], it[1]), velocity = Pair(it[2], it[3])) }
    }
}

private operator fun Pair<Int, Int>.plus(operand: Pair<Int, Int>) =
    Pair(this.first + operand.first, this.second + operand.second)

private operator fun Pair<Int, Int>.times(operand: Int) =
    Pair(this.first * operand, this.second * operand)