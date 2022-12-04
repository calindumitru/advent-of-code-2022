import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

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

fun printAnswer(question: String, answer: Int) = println("$question \nAnswer: \n$answer")