fun main() {
    val part1 = Implementation(
        question = "In how many assignment pairs does one range fully contain the other?",
        expectedAnswerForExample = 2
    ) { input ->
        val assignments = input.map { convert(it) }
        return@Implementation assignments.filter { it.fullyOverlaps() }.size
    }

    val part2 = Implementation(
        question = "In how many assignment pairs do the ranges overlap?",
        expectedAnswerForExample = 4
    ) { input ->
        val assignments = input.map { convert(it) }
        return@Implementation assignments.filter { it.havingOverlap() }.size
    }

    OhHappyDay(4).checkResults(part1, part2)
}

fun convert(line: String): Assignment {
    val (r1, r2) = line.split(",").toPair()
    return Assignment(toElfSection(r1), toElfSection(r2))
}

fun toElfSection(r: String): ElfSection {
    val (low, high) = r.split("-").toPair()
    return ElfSection(low.toInt(), high.toInt())
}

class Assignment(private val elf1: ElfSection, private val elf2: ElfSection) {
    fun fullyOverlaps(): Boolean {
        return elf1.contains(elf2) || elf2.contains(elf1)
    }
    fun havingOverlap(): Boolean {
        return elf1.overlaps(elf2)
    }
}
class ElfSection(private val min: Int, private val max: Int) {
    fun contains(second: ElfSection): Boolean {
        return this.min >= second.min && this.max <= second.max
    }
    fun overlaps(second: ElfSection): Boolean {
        return this.min <= second.max && this.max >= second.min
    }
}

