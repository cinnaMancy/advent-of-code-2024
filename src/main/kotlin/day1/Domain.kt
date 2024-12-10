package day1

import kotlin.math.abs

class NumberPairBatch(
    val lefts: List<Int>,
    val rights: List<Int>
) {
    fun sumDistance(): Int = lefts.sorted().zip(rights.sorted()).sumOf { (left, right) -> abs(left - right) }

    fun similarityScore(): Int = lefts.sumOf { left -> left * rights.count { right -> left == right } }

    companion object {
        fun parse(text: List<String>): NumberPairBatch {
            val lefts = text.map { it.substringBefore("   ") }.map(String::toInt)
            val rights = text.map { it.substringAfter("   ") }.map(String::toInt)
            return NumberPairBatch(lefts, rights)
        }
    }
}