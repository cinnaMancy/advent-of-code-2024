package day7

class Calibrations(
    val lines: List<Calibration>
) {
    fun possibleWithTwoOperatorSum(): Long =
        lines.filter { it.possible(twoOperators) }.map(Calibration::result).sum()

    fun possibleWithThreeOperatorSum(): Long =
        lines.filter { it.possible(threeOperators) }.map(Calibration::result).sum()

    companion object {
        val twoOperators: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times)
        val threeOperators: List<(Long, Long) -> Long> = twoOperators.plus { one, other -> "$one$other".toLong() }

        fun parse(lines: List<String>) = Calibrations(lines.map(Calibration::parse))
    }
}

class Calibration(
    val result: Long,
    val operands: List<Long>
) {
    fun possible(operators: List<(Long, Long) -> Long>): Boolean =
        possible(operands.first(), operands.drop(1), operators)

    private fun possible(current: Long, remainingOperands: List<Long>, operators: List<(Long, Long) -> Long>): Boolean =
        if (remainingOperands.isEmpty()) current == result
        else operators.any { operator ->
            possible(
                operator(current, remainingOperands.first()),
                remainingOperands.drop(1),
                operators
            )
        }

    companion object {
        fun parse(text: String): Calibration {
            val (result, operands) = """(\d+): (.*)${'$'}""".toRegex().matchEntire(text)?.destructured
                ?: throw IllegalArgumentException()
            return Calibration(
                result.toLong(),
                operands.split(" ").map(String::toLong)
            )
        }
    }
}