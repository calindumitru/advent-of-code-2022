import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.regex.Pattern

private val PATTERN = Pattern.compile("move (?<count>\\d+) from (?<source>\\d+) to (?<destination>\\d+)")

fun main() {
    val part1 = Implementation(
        question = "After the rearrangement procedure completes, what crate ends up on top of each stack?",
        expectedAnswerForExample = "CMZ"
    ) { lines ->
        val cargoPlane = convertCargo(lines)
        val instructions = lines.filter { s -> s.startsWith("move") }.map { convertInstruction(it) }
        println("Fresh cargo plane: $cargoPlane")
        cargoPlane.move(instructions)
        println("Cargo plane after moving crates: $cargoPlane")
        val topCrates = cargoPlane.getTopCrates()
        return@Implementation topCrates.joinToString(separator = "")
    }

    val part2 = Implementation(
        question = "After the rearrangement procedure completes, what crate ends up on top of each stack? (CM9001)",
        expectedAnswerForExample = "MCD"
    ) { lines ->
        val cargoPlane = convertCargo(lines)
        val instructions = lines.filter { s -> s.startsWith("move") }.map { convertInstruction(it) }
        cargoPlane.move(instructions, CrateMover.CM9001)
        val topCrates = cargoPlane.getTopCrates()
        return@Implementation topCrates.joinToString(separator = "")
    }
    OhHappyDay(5, part1, part2).checkResults()
}

fun convertCargo(it: List<String>): CargoPlane {
    val numbersLine = it.first { s -> !isCargoLine(s) && s.trim().isNotBlank() }
    val numberOfCrates = numbersLine.last().digitToInt()

    val cargoPlane = CargoPlane(numberOfCrates)

    it.takeWhile { s -> isCargoLine(s) }
        .reversed()
        .forEach{ line ->
        val chunks = line.chunked(4)
        for (crate in 1 .. numberOfCrates) {
            if (crate <= chunks.size) {
                val chunk = chunks[crate - 1]
                val crateContent = chunk.between("[", "]")
                if (crateContent.isNotBlank()) {
                    cargoPlane.addTo(crate, crateContent.first())
                }
            }
        }
    }
    return cargoPlane
}

private fun convertInstruction(line: String): Instruction {
    val matcher = PATTERN.matcher(line)
    if (matcher.find()) {
        return Instruction(matcher.group("source").toInt(),matcher.group("destination").toInt(), matcher.group("count").toInt())
    } else {
        throw IllegalStateException("Pattern not matched")
    }
}

private fun isCargoLine(s: String) = s.trim().startsWith('[')

data class CargoPlane(
    val numberOfBoxes: Int,
    private val boxes: MutableMap<Int, Deque<Char>> = mutableMapOf()
) {
    init {
        for(i in 1 .. numberOfBoxes) {
            this.boxes[i] = LinkedBlockingDeque()
        }
    }

    fun addTo(box: Int, char: Char) {
        boxes[box]!!.add(char)
    }

    private fun move(instruction: Instruction) {
        val fromBox = boxes[instruction.from]
        for (i in 0 until instruction.howMany) {
            fromBox?.onRemovable { boxes[instruction.to]?.add(it) }
        }
    }

    private fun moveMultiple(instruction: Instruction) {
        val fromBox = boxes[instruction.from]

        val pickedUp = (1..instruction.howMany).map { fromBox?.removeLast() }.reversed()

        boxes[instruction.to]?.addAll(pickedUp)
    }

    fun move(instructions: List<Instruction>, crane: CrateMover = CrateMover.CM9000) {
        instructions.forEach {
            when (crane) {
                CrateMover.CM9000 -> move(it)
                CrateMover.CM9001 -> moveMultiple(it)
            }
        }
    }

    fun getTopCrates(): List<Char> {
        return boxes.values.map { it.peekLast() }
    }
}

data class Instruction(val from: Int, val to: Int, val howMany: Int)

enum class CrateMover {
    CM9000,
    CM9001
}


