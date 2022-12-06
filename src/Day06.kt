import java.util.ArrayDeque

fun main() {
    val part1 = Implementation(
        "How many characters need to be processed before the first start-of-packet marker is detected?",
    7) {
        lines -> detectSignal(lines.first())
    }
    OhHappyDay(dayNumber = 6, part1).checkResults()
}

fun detectSignal(signal: String): Int {
    val arrayDeque = ArrayDeque<Char>()
    for (i in signal.indices) {
        arrayDeque.add(signal[i])
        if (arrayDeque.size == 4) {
            val set = HashSet<Char>(arrayDeque)
            println("Checking $set")
            if (set.size == 4) {
                return i + 1
            }
            arrayDeque.pop()
        }
    }
    throw IllegalStateException("Marker not found")
}
