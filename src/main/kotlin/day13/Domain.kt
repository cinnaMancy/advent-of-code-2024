package day13

class ClawMachines(
    private val machines: List<ClawMachine>
) {
    fun fewestTokensForAllPrizes(): Long = machines
        .mapNotNull { it.solution() }
        .sumOf { tokensCost(it) }

    fun fewestTokensForAllFarAwayPrizes(): Long = machines
        .map {
            ClawMachine(
                buttonA = it.buttonA,
                buttonB = it.buttonB,
                prize = it.prize + Pair(10000000000000L, 10000000000000L)
            )
        }
        .mapNotNull { it.cramersRuleLongs() }
        .sumOf { tokensCost(it) }

    private fun tokensCost(presses: Pair<Long, Long>) = 3 * presses.first + presses.second

    companion object {
        fun parse(lines: List<String>): ClawMachines = ClawMachines(lines.chunked(4).map(ClawMachine::parse))
    }
}

class ClawMachine(
    val buttonA: Pair<Long, Long>,
    val buttonB: Pair<Long, Long>,
    val prize: Pair<Long, Long>
) {
    fun solution(): Pair<Long, Long>? = (0L..100L).map { aPressed ->
        val xRemaining = prize.first - (aPressed * buttonA.first)
        val bPressed = xRemaining / buttonB.first
        return@map if ((buttonA * aPressed) + (buttonB * bPressed) == prize) Pair(aPressed, bPressed)
        else null
    }
        .filterNotNull()
        .firstOrNull()

    //  See: https://www.Longmath.com/matrices-determinants/1-determinants.php
    fun cramersRuleLongs(): Pair<Long, Long>? {
        val (a1, a2) = buttonA
        val (b1, b2) = buttonB
        val (c1, c2) = prize
        val abDet = (a1 * b2) - (b1 * a2)
        val acDet = (a1 * c2) - (c1 * a2)
        val cbDet = (c1 * b2) - (b1 * c2)
        return when {
            abDet == 0L -> null
            cbDet % abDet != 0L || acDet % abDet != 0L -> null
            else -> Pair(cbDet / abDet, acDet / abDet)
        }
    }

    companion object {
        fun parse(lines: List<String>): ClawMachine = ClawMachine(
            buttonA = """Button A: X\+(\d*), Y\+(\d*)""".toRegex().findAll(lines[0]).first().groupValues.drop(1)
                .map(String::toLong)
                .let { Pair(it.first(), it.last()) },
            buttonB = """Button B: X\+(\d*), Y\+(\d*)""".toRegex().findAll(lines[1]).first().groupValues.drop(1)
                .map(String::toLong)
                .let { Pair(it.first(), it.last()) },
            prize = """Prize: X=(\d*), Y=(\d*)""".toRegex().findAll(lines[2]).first().groupValues.drop(1)
                .map(String::toLong)
                .let { Pair(it.first(), it.last()) }
        )
    }
}

private operator fun Pair<Long, Long>.plus(operand: Pair<Long, Long>) =
    Pair(this.first + operand.first, this.second + operand.second)

private operator fun Pair<Long, Long>.times(operand: Long) =
    Pair(this.first * operand, this.second * operand)
