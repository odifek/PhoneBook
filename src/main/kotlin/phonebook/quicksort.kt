package phonebook

import java.util.*
import kotlin.Comparator
import kotlin.random.Random

fun <E> MutableList<E>.quickSort(comparator: Comparator<E>) {
    quickSort(0, size, comparator)
}

private fun <E> MutableList<E>.quickSort(start: Int, capacity: Int, comparator: Comparator<E>) {
    // []
    if (capacity <= 1) return
    val pivot = get(start + Random.nextInt(capacity))
    var leftUpperBound = start - 1 // Before the start
    var currentIndex = start
    var rightLowerBound = start + capacity // After the end of the current array partition
    while (currentIndex < rightLowerBound) {
        val comp = comparator.compare(get(currentIndex), pivot)
        if (comp < 0) {
            // Move to beginning of list
            swap(currentIndex++, ++leftUpperBound)
        } else if (comp > 0) {
            // Move to end of list
            swap(currentIndex, --rightLowerBound) // Current index remains in same position
        } else {
            // Keep in the middle.
            currentIndex++ // Merely move the current index pointer to the next position
        }
    }
    quickSort(start, leftUpperBound - start + 1, comparator)
    quickSort(rightLowerBound, capacity - (rightLowerBound - start), comparator)
}


fun main() {
    val scanner = Scanner(System.`in`)
    val list = mutableListOf(0, 5, 4, 1, 5, 8, 1)
    var iter = 0
    while (true) {
        val input = scanner.nextLine()
        if ("\\s*".toRegex().matches(input)) {
            list.quickSort { a, b -> a.compareTo(b) }
            println("Iteration ${++iter}\n$list")
        } else {
            break
        }
    }
}
