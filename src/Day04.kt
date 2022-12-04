fun main() {
    fun part1(input: List<String>): Int {
        val assignments = input.map { convert(it) }
        return assignments.filter { it.fullyOverlaps() }.size
    }

    fun part2(input: List<String>): Int {
        val assignments = input.map { convert(it) }
        return assignments.filter { it.havingOverlap() }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4_example")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day4")
    printAnswer("In how many assignment pairs does one range fully contain the other?", part1(input))
    printAnswer("In how many assignment pairs do the ranges overlap?", part2(input))
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

