package phonebook


fun main() {
    println(f(arrayOf(2, 2, 2, 2, 2), 0, 4, 2))
    println(f(arrayOf(1, 2, 3, 3, 3), 0, 4, 3))
    println(f(arrayOf(1, 2, 3, 4, 5), 0, 4, 5))
    println(f(arrayOf(1, 2, 3, 5, 5), 0, 5, 5))
    println(f(arrayOf(1, 1, 1, 2, 2), 1, 3, 1))
}

private fun f(array: Array<Int>, left: Int, right: Int, value: Int): Int {
    if (left >= right) return 0
    if (left == right - 1) {
        return if (array[left] == value) 1 else 0
    }
    val middle = (left + right) / 2
    return f(array, left, middle, value) + f(array, middle, right, value)
}