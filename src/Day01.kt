import kotlin.math.max

fun main() {
    val part1 = Implementation("Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?",
        24000) { lines ->
        val joinToString = lines.joinToString("+")
        println(joinToString)
        val maxCalories = joinToString.split("++")
            .filter { it.isNotBlank() }.maxOf { elf -> elf.split("+").sumOf { it.toInt() } }

        println("calories: $maxCalories")
        return@Implementation maxCalories
    }
    OhHappyDay(1, part1).checkResults()
}
