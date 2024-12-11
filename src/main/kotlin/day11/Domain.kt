package day11

class StoneLine(
    private val stones: List<Stone>
) {
    fun stonesCountAfter25Evolutions(): Int = (1..25).fold(stones) { it, _ -> evolve(it) }.size

    fun stonesCountAfter75Evolutions(): Int = TODO()

    private fun evolve(stones: List<Stone>): List<Stone> = stones.flatMap(Stone::evolve)

    companion object {
        fun parse(text: String): StoneLine = StoneLine(text.split(" ").map(String::toLong).map(::Stone))
    }

    data class Stone(
        val content: Long
    ) {
        fun evolve(): List<Stone> =
            if (evolutionCache.contains(content)) evolutionCache[content]!!
            else {
                val result = if (content == 0L) listOf(Stone(1))
                else if (content.toString().length % 2 == 0)
                    content.toString().chunked(content.toString().length / 2).map(String::toLong).map(::Stone)
                else listOf(Stone(2024 * content))
                evolutionCache[content] = result
                result
            }

        companion object {
            val evolutionCache = mutableMapOf<Long, List<Stone>>()
        }
    }
}
