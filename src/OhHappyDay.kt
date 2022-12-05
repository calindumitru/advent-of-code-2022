class OhHappyDay(private val dayNumber: Int) {

    fun checkResults(part1: Implementation<*>, part2: Implementation<*>) {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput("day${dayNumber}_example")
        checkImplementation(part1, testInput)
        checkImplementation(part2, testInput)

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

val emptyImplementation = Implementation("Haven't reached part 2", 0) {
    return@Implementation 0
}