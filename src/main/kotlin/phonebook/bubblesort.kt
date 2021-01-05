package phonebook

import java.util.concurrent.TimeoutException


fun <E> MutableList<E>.bubbleSort(timelimit: Long = 0, comparator: Comparator<E>) {

    val startTime = System.currentTimeMillis()
    var unsortedEnd = size - 1
    while (unsortedEnd > 0) {
        for (i in 1..unsortedEnd) {
            if (comparator.compare(get(i - 1), get(i)) > 0) {
                swap(i - 1, i)
                if (timelimit > 0 && (System.currentTimeMillis() - startTime) > timelimit) {
                    // Stop the bubble sort
                    throw TimeoutException("Sort time limit exceeded!")
                }
            }
        }
        unsortedEnd--
    }

}

fun main() {
    val list = mutableListOf(7, 2, 1, 4, 6, 1, 0, 4, 1, 23, 5, 65, 123, 67, 3)
    list.bubbleSort { a, b -> a.compareTo(b) }
    println("$list")
}

