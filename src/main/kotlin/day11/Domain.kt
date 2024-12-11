package day11

class StoneLine(
    private val stones: List<Long>
) {
    fun stonesCountAfterEvolutions(times: Int): Long = stones.sumOf { sizeAfterEvolutions(it, times) }

    private fun sizeAfterEvolutions(stone: Long, times: Int): Long {
        if (times == 0) return 1

        val memo = evolutionsMemo[Pair(stone, times)]
        if (memo != null) return memo

        val nextElements =
            if (stone == 0L) listOf(1L)
            else if (stone.toString().length % 2 == 0)
                stone.toString().chunked(stone.toString().length / 2).map(String::toLong)
            else listOf(2024 * stone)

        val nextResults = nextElements.sumOf { sizeAfterEvolutions(it, times - 1) }
        evolutionsMemo[Pair(stone, times)] = nextResults
        return nextResults
    }

    companion object {
        val evolutionsMemo = mutableMapOf<Pair<Long, Int>, Long>()

        fun parse(text: String): StoneLine = StoneLine(text.split(" ").map(String::toLong))
    }
}
