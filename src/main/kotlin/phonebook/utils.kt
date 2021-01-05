package phonebook

import java.io.File

fun <E> MutableList<E>.swap(i1: Int, i2: Int) {
    val first = get(i1)
    val second = get(i2)
    set(i1, second)
    set(i2, first)
}


fun timeBreakdown(totalTime: Long) =
        "${totalTime / 60_000} min. ${(totalTime % 60_000) / 1000} sec. ${(totalTime % 60_000) % 1000} ms"

fun <T> readFile(filePath: String, mapper: (String) -> T?): List<T> {
    return File(filePath).readLines().mapNotNull(mapper)
}
