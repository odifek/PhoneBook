package phonebook


sealed class Result(open val searchTime: Long,
                    open val found: Int,
                    open val total: Int) {
    abstract fun message(): String
}

data class SearchResult(
        val sortTime: Long,
        override val searchTime: Long,
        override val found: Int,
        override val total: Int
) : Result(searchTime, found, total) {
    override fun message() = """
        Found $found/$total entries. Time taken: ${timeBreakdown(searchTime + sortTime)}.
        Sorting time: ${timeBreakdown(sortTime)}.
        Searching time: ${timeBreakdown(searchTime)}
        
    """.trimIndent()
}

data class LinearSearchResult(
        override val searchTime: Long,
        override val found: Int,
        override val total: Int
) : Result(searchTime, found, total) {
    override fun message(): String = """
        Found $found/$total entries. Time taken: ${timeBreakdown(searchTime)}.
        
    """.trimIndent()

}

data class HashtableSearchResult(
        val createTime: Long,
        override val searchTime: Long,
        override val found: Int,
        override val total: Int
) : Result(searchTime, found, total) {
    override fun message() = """
        Found $found/$total entries. Time taken: ${timeBreakdown(searchTime + createTime)}.
        Creating time: ${timeBreakdown(createTime)}.
        Searching time: ${timeBreakdown(searchTime)}
    """.trimIndent()
}
