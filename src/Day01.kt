fun main() {
    val part1 = Implementation("Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?",
        24000) { lines ->
        return@Implementation computeCaloriesByElf(lines).max()
    }
    val part2 = Implementation("Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?",
        45000) { lines ->
        return@Implementation computeCaloriesByElf(lines).sortedDescending().take(3).sum()
    }
    OhHappyDay(1, part1, part2).checkResults()
}

fun computeCaloriesByElf(lines: List<String>): List<Int> {
    //c1elf1+c2elf1++c1elf2+c2elf2+c3elf2++c1elf3...
     val allCalories = lines.joinToString("+")
    return allCalories.split("++")
        .filter { calorieValuesConcatenatedByPlus -> calorieValuesConcatenatedByPlus.isNotBlank() }
        .map{elf -> elf.split("+")
            .sumOf { calorieValue -> calorieValue.toInt() }}
}