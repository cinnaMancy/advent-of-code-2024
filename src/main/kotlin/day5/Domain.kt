package day5

class UpdateBatch(
    val updates: List<Update>,
    val rules: List<Rule>
) {
    fun correctsMiddleSum(): Int = updates
        .filter(::passes)
        .map(Update::middleNumber)
        .sum()

    fun incorrectsCorrectedMiddleSum(): Int = updates
        .filter { !passes(it) }
        .map { corrected(it) }
        .map(Update::middleNumber)
        .sum()

    private fun passes(update: Update): Boolean = rules.all { rule -> rule.passes(update) }

    private fun corrected(update: Update): Update {
        if (passes(update)) {
            return update
        }
        val firstFailingRule = rules.first { !it.passes(update) }
        val ruleLeftIndex = update.indexOf(firstFailingRule.left)
        val ruleRightIndex = update.indexOf(firstFailingRule.right)
        val pagesSwappedByRule = update.toMutableList().swap(ruleLeftIndex, ruleRightIndex)
        return corrected(Update(pagesSwappedByRule))
    }

    companion object {
        fun parse(rulesAndUpdatesTextLines: List<String>): UpdateBatch {
            val splitIndex = rulesAndUpdatesTextLines.indexOf("")
            val rulesTextLines = rulesAndUpdatesTextLines.subList(0, splitIndex)
            val updatesTextLines = rulesAndUpdatesTextLines.subList(splitIndex + 1, rulesAndUpdatesTextLines.size)
            return UpdateBatch(
                updatesTextLines.map { Update.parse(it) },
                rulesTextLines.map { Rule.parse(it) },
            )
        }
    }
}

class Update(
    val pages: List<Int>
) : List<Int> by pages {
    fun middleNumber(): Int = pages[pages.size / 2]

    companion object {
        fun parse(updateText: String): Update = Update(updateText.split(",").map(String::toInt))
    }
}

class Rule(
    val left: Int,
    val right: Int
) {
    fun passes(update: Update): Boolean =
        if (update.contains(left) && update.contains(right)) update.indexOf(left) < update.indexOf(right)
        else true

    companion object {
        fun parse(ruleText: String): Rule {
            val numbers = ruleText.split("|").map(String::toInt)
            return Rule(numbers[0], numbers[1]);
        }
    }
}

fun <E> MutableList<E>.swap(firstIndex: Int, secondIndex: Int): MutableList<E> {
    this[firstIndex] = this[secondIndex].also { this[secondIndex] = this[firstIndex] }
    return this
}