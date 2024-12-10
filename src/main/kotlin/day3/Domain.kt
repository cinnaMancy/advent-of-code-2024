package day3

class Program(
    memoryText: String
) {
    val memory = memoryText.lines().joinToString("")

    fun multiplicationsSum(): Int =
        """mul\(\d+,\d+\)""".toRegex().findAll(memory)
            .map(MatchResult::value)
            .map(Multiplication.Companion::parse)
            .map(Multiplication::result).sum()

    fun enabledMultiplicationsSum(): Int {
        val enabledText = memory.replace("""don't\(\).*?(?:do\(\)|${'$'})""".toRegex(), "")
        return Program(enabledText).multiplicationsSum()
    }
}

class Multiplication(
    val left: Int,
    val right: Int
) {
    fun result() = left * right

    companion object {
        fun parse(text: String): Multiplication {
            val numbers = text.removePrefix("mul(").removeSuffix(")").split(",").map(String::toInt)
            return Multiplication(numbers[0], numbers[1])
        }
    }
}