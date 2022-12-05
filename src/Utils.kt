import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> List<T>.toPair(): Pair<T, T> {
    check(this.size >= 2)
    return (this[0] to this[1])
}
fun String.between(first: String, second: String): String {
    return this.substringAfter(first).substringBefore(second)
}

fun <T> Deque<T>.onRemove(f: (T) -> Unit) {
    f.invoke(this.removeLast())
}
fun <T> Deque<T>.onRemovable(f: (T) -> Unit) {
    if (this.size > 0) {
        this.onRemove(f)
    }
}