package phonebook

fun <E> List<E>.bSearch(compare: (E) -> Int): Int {
    if (size == 0) return -1
    if (size == 1) {
        return if (compare(first()) == 0) 0 else -1
    }
    return bSearch(0, size - 1, compare)
}

private fun <E> List<E>.bSearch(left: Int, right: Int, compare: (E) -> Int): Int {
    if (left > right) return -1
    if (left == right) {
        return if (compare(get(left)) == 0) left else -1
    }
    val middle = (left + right) / 2
    if (compare(get(middle)) == 0) {
        return middle
    } else if (compare(get(middle)) < 0) {
        return bSearch(left, middle - 1, compare)
    } else {
        return bSearch(middle + 1, right, compare)
    }
}

fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val search = list.bSearch { 10.compareTo(it) }
    println("Result: $search")
}