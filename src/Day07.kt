import java.util.function.Predicate

const val TOTAL = 70000000
const val UNUSED = 30000000
fun main() {
    val part1 = Implementation("Find all of the directories with a total size of at most 100000. What is the sum of the total sizes of those directories?",
        95437) { lines ->
        val root = convertToGraph(lines)

        return@Implementation root.find { t -> t.completeSize() <= 100000 }.sumOf { it.completeSize() }
    }

    val part2 = Implementation("Find the smallest directory that, if deleted, would free up enough space on the filesystem to run the update. What is the total size of that directory?",
        24933642) { lines ->
        val root = convertToGraph(lines)

        val limit = UNUSED - (TOTAL - root.completeSize())
        return@Implementation root.find { t -> t.completeSize() >= limit }.minOf { it.completeSize() }
    }

    OhHappyDay(7, part1, part2).checkResults()
}

fun convertToGraph(lines: List<String>): Dir {
    val root = Dir(name = "/")
    var current = root
    lines.forEach { line ->
        when {
            line == "$ cd /" -> {}
            line == "$ cd .." -> {
                current = current.parent!!
            }
            line.startsWith("$ cd") -> {
                val dirName = line.substringAfter("cd ")
                val dir = Dir(name = dirName)
                dir.parent = current
                current.add(dir)
                current = dir
            }
            line.first().isDigit() -> {
                val value = line.substringBefore(" ").toInt()
                current.size += value
            }
            line == "$ ls" -> {}
        }
    }
    return root
}

data class Dir(val name:String,
          val children: MutableList<Dir> = mutableListOf(),
          var size:Int = 0) {
    var parent: Dir? = null

    fun add(d: Dir) {
        children.add(d)
    }

    fun find(p: Predicate<Dir>): List<Dir> {
        val flattened = flatten()
        return flattened.filter { p.test(it) }
    }
    private fun flatten(): List<Dir> = children + children.flatMap { it.flatten() }
    
    fun completeSize() : Int = this.size + children.sumOf { cc -> cc.completeSize() }
}