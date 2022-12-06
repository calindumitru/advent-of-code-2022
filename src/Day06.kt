import java.util.ArrayDeque

fun main() {
    val part1 = Implementation(
        "How many characters need to be processed before the first start-of-packet marker is detected?",
    10) {
        lines -> detectSignal(lines.first())
    }
    val part2 = Implementation(
        "How many characters need to be processed before the first start-of-message marker is detected?",
    29) {
        lines -> detectSignal(lines.first(), 14)
    }
    OhHappyDay(dayNumber = 6, part1, part2).checkResults()
}

fun detectSignal(signal: String, bufferSize: Int = 4): Int {
    val arrayDeque = ArrayDeque<Char>()
    for (i in signal.indices) {
        arrayDeque.add(signal[i])
        if (arrayDeque.size == bufferSize) {
            val set = HashSet<Char>(arrayDeque)
            println("Checking $set")
            if (set.size == bufferSize) {
                return i + 1
            }
            arrayDeque.pop()
        }
    }
    throw IllegalStateException("Marker not found")
}
