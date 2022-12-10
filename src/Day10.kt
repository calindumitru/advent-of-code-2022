import java.util.*

fun main() {
    val part1 = Implementation("Find the signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles. What is the sum of these six signal strengths?",
        13140) { lines ->
        val instructions = mapInstructions(lines)
        val process = Process(instructions)
        process.processInstructions()
        return@Implementation process.getSnapshotsSum()
    }

    val part2 = Implementation("Render the image given by your program. What eight capital letters appear on your CRT?",
        "NO ANSWER") { lines ->
        val instructions = mapInstructions(lines)
        val process = Process(instructions)
        process.processMonitor()
        process.draw()
        return@Implementation "NO ANSWER"
    }

    OhHappyDay(10, part1, part2).checkResults()
}

data class Process(val instructions: List<Op>) {
    var x = 1
    val snapshots = mutableListOf<Pair<Int,Int>>()
    val monitor = mutableListOf<CharArray>()

    private fun newLine() = "........................................".toCharArray()

    fun processInstructions() {
        val q = LinkedList(instructions)
        for (i in 1 .. 220) {
            if (i == 20 || (i+20) % 40 == 0) {
                snapshots.add  (i to x)
            }
            val instruction = q.first()
            instruction.entropy()
            if (instruction.cycle == 0) {
                x = instruction.apply(x)
                q.removeFirst()
            }
        }
    }

    fun processMonitor() {
        val q = LinkedList(instructions)
        var currentLine = newLine()
        for (i in 0 until 240) {
            val pixelPosition = i % 40
            if (pixelPosition == 0) {
                currentLine = newLine()
                monitor.add(currentLine)
            }
            val instruction = q.first()
            instruction.entropy()
            if (pixelPosition in (x-1)..(x+1)) {
                currentLine[pixelPosition] = '#'
            }
            if (instruction.cycle == 0) {
                x = instruction.apply(x)
                q.removeFirst()
            }
        }
    }

    fun draw() {
        monitor.forEach{
            println(String(it))
        }
    }

    fun getSnapshotsSum(): Int {
        //println(snapshots)
        return snapshots.sumOf { it.first * it.second }
    }
}

fun mapInstructions(lines: List<String>): List<Op> {
    return lines.map {
        when {
            it == "noop" -> Op(Operation.NOOP, 0)
            it.startsWith("addx") -> Op(Operation.ADD, it.substringAfter(" ").toInt(), 2)
            else -> throw IllegalArgumentException()
        }
    }
}

data class Op(val op: Operation, val value : Int = 0, var cycle: Int = 1) {
    fun entropy() {
        cycle--
    }

    fun apply(x: Int): Int {
        return when(op) {
            Operation.NOOP -> x
            Operation.ADD -> x + value
        }
    }
}


enum class Operation {
    ADD,
    NOOP
}