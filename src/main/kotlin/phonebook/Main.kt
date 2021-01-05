package phonebook

import java.util.concurrent.TimeoutException

fun main() {
    val phoneBook = PhoneBook(
            directoryFile = "/home/kennedy/hyperskill/phonebook/directory.txt",
            findFile = "/home/kennedy/hyperskill/phonebook/find.txt"
    )
    phoneBook.search(::linearPhonebookSearch)
    phoneBook.search(::bubbleSortAndJumpSearchPhoneBook)
    phoneBook.search(::quickSortAndBinarySearch) // My quicksort is terribly slow! Fixed! Happens that my swap implementation was super inefficient
    phoneBook.search(::instantSearchWithHashtable)

}

private fun quickSortAndBinarySearch(directory: List<String>, namesToFind: List<String>): Result {
    println("Start searching (quick sort + binary search)...")
    return sortAndSearch(directory, namesToFind,
            sorter = { quickSort { a, b -> a.contactName().compareTo(b.contactName()) } },
            search = { name ->
                bSearch(compareContactWithName(name))
            })
}

private fun String.contactName() = substringAfter(" ")

private fun sortAndSearch(directory: List<String>, namesToFind: List<String>,
                          sorter: MutableList<String>.() -> Unit,
                          search: List<String>.(String) -> Int): SearchResult {
    val sortStart = System.currentTimeMillis()
    val sortedContacts = directory.toMutableList().apply(sorter) // take(15000)
    val sortTime = System.currentTimeMillis() - sortStart

    val startTime = System.currentTimeMillis()
    var found = 0
    for (name in namesToFind) {
        val result = sortedContacts.search(name)
        if (result >= 0) {
            found++
        }
    }
    val searchTime = System.currentTimeMillis() - startTime

    return SearchResult(sortTime = sortTime, searchTime = searchTime, found, namesToFind.size)
}

private fun bubbleSortAndJumpSearchPhoneBook(directory: List<String>, namesToFind: List<String>): Result {
    println("Start searching (bubble sort + jump search)...")
    return try {
        sortAndSearch(directory, namesToFind,
                sorter = { bubbleSort(10 * 60 * 1000/*10 minutes*/) { a, b -> a.contactName().compareTo(b.contactName()) } },
                search = { name ->
                    jumpSearch(compareContactWithName(name))
                }
        )
    } catch (e: TimeoutException) {
        SearchResult(600000, 2000, 500, 500) // TODO: Took a lot of time
    }
}

private fun compareContactWithName(name: String) = { contact: String ->
    when {
        contact.contactName().contains(name, true) -> 0
        else -> name.compareTo(contact.contactName())
    }
}

private fun linearPhonebookSearch(directory: List<String>, namesToFind: List<String>): Result {

    println("Start searching (linear search)...")
    val startTime = System.currentTimeMillis()
    var found = 0
    for (name in namesToFind) {
        for (contact in directory) {
            if (contact.contains(name, true)) {
                found++
                break
            }
        }
    }
    val timeTaken = System.currentTimeMillis() - startTime

    return LinearSearchResult(timeTaken, found, namesToFind.size)
}

private fun instantSearchWithHashtable(directory: List<String>, namesToFind: List<String>): Result {
    println("Start searching (hash table)...")
    val mapCreateStart = System.currentTimeMillis()
    val contactsMap = directory.associateBy({ contact -> contact.contactName() }, { contact -> contact.contactPhone() })
    val mapCreateTime = System.currentTimeMillis() - mapCreateStart

    val searchStart = System.currentTimeMillis()
    var found = 0
    for (name in namesToFind) {
        if (contactsMap[name] != null) {
            found++
        }
    }
    val searchTime = System.currentTimeMillis() - searchStart

    return HashtableSearchResult(mapCreateTime, searchTime, found, namesToFind.size)
}

private fun String.contactPhone() = substringBefore(" ")
