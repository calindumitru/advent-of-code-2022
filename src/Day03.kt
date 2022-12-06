fun main() {
    val part1 = Implementation("Find the item type that appears in both compartments of each rucksack. What is the sum of the priorities of those item types?",
        157) { lines ->
        val sum = lines.map { it.chunked(it.length / 2) }
            .map { twoChunks -> (twoChunks.get(0).toCharSet() to twoChunks.get(1).toCharSet()) }
            .map { (a, b) -> (a.intersect(b)) }
            .flatten().sumOf { calculatePriority(it) }
        return@Implementation sum
    }
    val part2 = Implementation("Find the item type that corresponds to the badges of each three-Elf group. What is the sum of the priorities of those item types?",
        70) { lines ->
        val sum = lines.chunked(3)
            .map { it.get(0).toCharSet().intersect(it.get(1).toCharSet()).intersect(it.get(2).toCharSet()) }
            .flatten().sumOf { calculatePriority(it) }
        return@Implementation sum
    }

    OhHappyDay(3, part1, part2).checkResults()
}

fun calculatePriority(it: Char): Int {
    return if (it.isLowerCase()) {
        pos(it)
    } else {
        pos(it.lowercaseChar()) + 26
    }
}

private fun pos(it: Char) = (it.code - 97 + 1)

private fun String.toCharSet() = this.toCharArray().toSet()