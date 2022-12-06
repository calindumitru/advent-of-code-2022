import java.util.*

fun main() {
    val part1 = Implementation("Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?",
        157) { lines ->
        val sum = lines.map { it.chunked(it.length / 2) }
            .map { twoChunks -> (twoChunks.get(0).toCharArray() to twoChunks.get(1).toCharArray()) }
            .map { (a, b) -> (a.toSet().intersect(b.toSet())) }
            .flatten().sumOf { calculate(it) }
        return@Implementation sum
    }

    OhHappyDay(3, part1, emptyImplementation).checkResults()
}

fun calculate(it: Char): Int {
    return if (it.isLowerCase()) {
        pos(it)
    } else {
        pos(it.lowercaseChar()) + 26
    }
}

private fun pos(it: Char) = (it.code - 97 + 1)
