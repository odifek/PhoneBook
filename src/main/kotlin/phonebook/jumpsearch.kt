package phonebook

import kotlin.math.sqrt

/**
 * Makes a search in the list using the jump search algorithm and returns the index of the item or -1 if not found
 * @param compare use to compare the current item with the search item. negative if item of interest is after current, zero if item matches current, positive if item is before current
 */
fun <T> List<T>.jumpSearch(compare: (T) -> Int): Int {
    if (compare(this[0]) == 0) return 0
    if (size == 1) return -1
    val blockSize = sqrt(size.toDouble()).toInt()
    val numBlocks = (size - 1) / blockSize

    var currentBlock = 1
    while (currentBlock <= numBlocks) {
        val blockEnd = (currentBlock * blockSize).coerceAtMost(size - 1)
        val blockStart = (blockEnd - blockSize + 1).coerceAtLeast(1)
        // Only search the block if the end block is greater or equal to the search term
        if (compare(this[blockEnd]) >= 0) {
            for (i in blockEnd downTo blockStart) {
                if (compare(this[i]) == 0) {
                    return i
                }
            }
        }
        currentBlock++
    }
    return -1
}
