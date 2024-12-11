package day11

class StoneLine(
    private val stones: List<Stone>
) {
    fun stonesCountAfterEvolutions(times: Int): Int = stones.sumOf { it.evolve(times).size }


    companion object {
        fun parse(text: String): StoneLine = StoneLine(text.split(" ").map(String::toLong).map(::Stone))
    }

    data class Stone(
        val content: Long
    ) {
        fun evolve(times: Int): List<Stone> {
            if (times == 0) return listOf(this)

            val memo = evolutionsMemo[Pair(this, times)]
            if (memo != null) return memo

            val nextElements =
                if (content == 0L) listOf(Stone(1))
                else if (content.toString().length % 2 == 0)
                    content.toString().chunked(content.toString().length / 2).map(String::toLong).map(::Stone)
                else listOf(Stone(2024 * content))
            
            val nextResults = nextElements.flatMap { it.evolve(times - 1) }
            evolutionsMemo[Pair(this, times)] = nextResults
            return nextResults
        }

        companion object {
            val evolutionsMemo = mutableMapOf<Pair<Stone, Int>, List<Stone>>()
        }
    }
}
