class OhHappyDay(private val dayNumber: Int,
                 private val part1: Implementation<*>,
                 private val part2: Implementation<*> = emptyImplementation) {
    fun checkResults() {
        // test if implementation meets criteria from the description
        val testInput = readInput("day${dayNumber}_example")
        checkImplementation(part1, testInput)
        checkImplementation(part2, testInput)

        //test implementation against the puzzle input
        val input = readInput("day${dayNumber}")
        part1.printAnswer(input)
        if (part2 !== emptyImplementation) {
            part2.printAnswer(input)
        }
    }

    private fun checkImplementation(impl: Implementation<*>, testInput: List<String>) {
        val result = impl.implementationFunction(testInput)
        check(result == impl.expectedAnswerForExample) { "Result $result not equal to expected: ${impl.expectedAnswerForExample}" }
    }
}

class Implementation<T>(
    val question: String,
    val expectedAnswerForExample: T,
    val implementationFunction: (List<String>) -> T
) {
    fun printAnswer(input: List<String>) = println("$question \nAnswer: \n${implementationFunction(input)}")

}

val emptyImplementation = Implementation("Haven't reached this part..", 0) {
    return@Implementation 0
}