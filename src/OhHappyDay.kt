class OhHappyDay(private val dayNumber: Int) {

    fun checkResults(part1: Implementation, part2: Implementation) {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput("day${dayNumber}_example")
        checkImplementation(part1, testInput)
        checkImplementation(part2, testInput)

        val input = readInput("day${dayNumber}")
        part1.printAnswer(input)
        part2.printAnswer(input)
    }

    private fun checkImplementation(impl: Implementation, testInput: List<String>) {
        check(impl.implementationFunction(testInput) == impl.expectedAnswerForExample)
    }
}

class Implementation(
    val question: String,
    val expectedAnswerForExample: Int,
    val implementationFunction: (List<String>) -> Int
) {
    fun printAnswer(input: List<String>) = println("$question \nAnswer: \n${implementationFunction(input)}")

}