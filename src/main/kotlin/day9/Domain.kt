package day9

class FileSystem(
    val memory: List<MemoryCell>
) {
    fun checksumPiecewise(): Long = fragmentPiecewise().checksum()

    fun checksumWhole(): Long = fragmentWhole().checksum()

    private fun fragmentPiecewise(): List<MemoryCell> = generateSequence(memory) { memory ->
        val firstSpaceIndex = memory.indexOfFirst { it.file == null }
        val lastFileIndex = memory.indexOfLast { it.file != null }
        if (firstSpaceIndex < lastFileIndex) memory.toMutableList().swap(firstSpaceIndex, lastFileIndex)
        else null
    }.last()

    private fun fragmentWhole(): List<MemoryCell> =
        generateSequence(Pair(memory.maxOf { it.file ?: -1L }, memory)) { (currentFile, currentMemory) ->
            if (currentFile < 0) return@generateSequence null
            val fileStartIndex = currentMemory.indexOfFirst { it.file == currentFile }
            val fileEndIndex = currentMemory.indexOfLast { it.file == currentFile }
            val fileSize = fileEndIndex - fileStartIndex + 1
            val availableSpaceIndex = currentMemory.firstIndexOfSpaceBlockOfSize(fileSize)
            if (availableSpaceIndex == -1 || availableSpaceIndex > fileStartIndex) return@generateSequence Pair(
                currentFile - 1,
                currentMemory
            )
            else return@generateSequence Pair(
                currentFile - 1,
                currentMemory.toMutableList().swapRange(availableSpaceIndex, fileStartIndex, fileSize)
            )
        }
            .map(Pair<Long, List<MemoryCell>>::second)
            .last()

    private fun List<MemoryCell>.firstIndexOfSpaceBlockOfSize(fileSize: Int): Int =
        this.withIndex()
            .filter { (_, cell) -> cell.file == null }
            .firstOrNull { (index, _) ->
                (0..<fileSize).all { index + it in memory.indices && memory[index + it].file == null }
            }
            ?.index
            ?: -1


    private fun List<MemoryCell>.checksum(): Long = this
        .mapIndexed { index, cell -> index * (cell.file ?: 0L) }
        .sum()

    private fun <T> MutableList<T>.swap(firstIndex: Int, secondIndex: Int): MutableList<T> =
        this.apply { this[firstIndex] = this[secondIndex].also { this[secondIndex] = this[firstIndex] } }

    private fun <T> MutableList<T>.swapRange(firstIndex: Int, secondIndex: Int, range: Int): MutableList<T> =
        this.apply { (0..<range).forEach { swap(firstIndex + it, secondIndex + it) } }

    companion object {
        fun parse(text: String): FileSystem = text.map(Char::digitToInt).flatMapIndexed { fileIndex, digit ->
            if (fileIndex % 2 == 0) List(digit) { MemoryCell(fileIndex / 2L) }
            else List(digit) { MemoryCell(null) }
        }.let(::FileSystem)
    }

    data class MemoryCell(
        val file: Long?
    )
}