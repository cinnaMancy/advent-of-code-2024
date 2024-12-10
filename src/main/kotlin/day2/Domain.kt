package day2

class ReportBatch(
    val reports: List<Report>
) {
    fun safeReports(): Int = reports.count(Report::isSafe)

    fun safeReportsToleratingOne(): Int = reports.count(Report::isSafeToleratingOne)

    companion object {
        fun parse(text: List<String>): ReportBatch = ReportBatch(text.map { Report.parse(it) })
    }
}

class Report(
    val levels: List<Int>
) {
    fun isSafe(): Boolean = levels.withIndex().run { all(::safeUp) || all(::safeDown) }

    fun isSafeToleratingOne(): Boolean = levels.indices
        .map { levels.toMutableList().apply { removeAt(it) } }
        .map { Report(it) }
        .any(Report::isSafe)

    private fun safeUp(level: IndexedValue<Int>): Boolean = safeInDirection(level, 1)

    private fun safeDown(level: IndexedValue<Int>): Boolean = safeInDirection(level, -1)

    private fun safeInDirection(level: IndexedValue<Int>, directionMultiplier: Int) =
        level.index == 0 || directionMultiplier * (level.value - levels[level.index - 1]) in 1..3

    companion object {
        fun parse(text: String): Report = Report(text.split(" ").map(String::toInt))
    }
}