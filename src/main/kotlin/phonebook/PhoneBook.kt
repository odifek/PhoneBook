package phonebook

class PhoneBook(directoryFile: String, val findFile: String) {

    private val contactRegex = "(\\d+)\\s+([\\w|\\s]+)".toRegex()
    private val contactPattern = contactRegex.toPattern()

    private val unsortedContacts: List<String> = readFile<String>(directoryFile) { it }

//    private val unsortedContacts: List<Contact> = readFile<Contact>(directoryFile) { line ->
//        val contactInfo = contactPattern.matcher(line)
//        if (contactInfo.find()) {
//            Contact(contactInfo.group(1)!!, contactInfo.group(2)!!)
//        } else {
//            null
//        }
//    }

    private val namesToFind = readFile(findFile) { it }

    fun search(algorithm: (List<String>, List<String>) -> Result) {
        val result = algorithm(unsortedContacts, namesToFind)
        println(result.message())
    }
}