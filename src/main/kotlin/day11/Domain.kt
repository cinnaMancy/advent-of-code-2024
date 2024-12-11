package day11

class StoneLine(
    private val stones: List<Long>
) {
    fun stonesCountAfterEvolutions(times: Int): Int = stones.sumOf { evolve(it, times).size }

    private fun evolve(stone: Long, times: Int): List<Long> {
        if (times == 0) return listOf(stone)

        val memo = evolutionsMemo[Pair(stone, times)]
        if (memo != null) return memo

        val nextElements =
            if (stone == 0L) listOf(1L)
            else if (stone.toString().length % 2 == 0)
                stone.toString().chunked(stone.toString().length / 2).map(String::toLong)
            else listOf(2024 * stone)

        val nextResults = nextElements.flatMap { evolve(it, times - 1) }
        evolutionsMemo[Pair(stone, times)] = nextResults
        return nextResults
    }

    companion object {
        val evolutionsMemo = mutableMapOf<Pair<Long, Int>, List<Long>>()

        fun parse(text: String): StoneLine = StoneLine(text.split(" ").map(String::toLong))
    }
}
