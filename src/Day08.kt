fun main() {
    val part1 = Implementation("Consider your map; how many trees are visible from outside the grid?",
        21) { lines ->
        val map = convertToMap(lines)
        return@Implementation map.countVisible()
    }

    val part2 = Implementation("Consider your map; how many trees are visible from outside the grid?",
        8) { lines ->
        val map = convertToMap(lines)
        return@Implementation map.calculateScenicScore()
    }

    OhHappyDay(8, part1, part2).checkResults()
}

fun convertToMap(lines: List<String>): Map {
    val map = Map(lines.first().length , lines.size)
    lines.forEachIndexed {lineIndex, line -> line.toCharArray()
        .forEachIndexed { charIndex, c -> map.add(lineIndex, charIndex, c.digitToInt()) }}
    return map
}

data class Map(private val width: Int, private val height: Int, private val matrix: Array<Array<Int?>?> = arrayOfNulls(height)) {
    init {
        for (i in 0 until height) {
            matrix[i] = arrayOfNulls(width)
        }
    }
    fun add(x: Int ,y:Int, tree: Int) {
        matrix[x]!![y] = tree
    }

    fun isVisible(x: Int ,y:Int, tree: Int): Boolean {
        if (x == 0 || y == 0 || x == width -1 || y == height -1) {
            return true
        }

        var visibleRight = true
        for (i in x+1 until width) {
            if (matrix[i]!![y]!! >= tree) {
                visibleRight = false
            }
        }
        if (visibleRight) return true

        var visibleLeft = true
        for (i in 0 until x) {
            if (matrix[i]!![y]!! >= tree) {
                visibleLeft = false
                break
            }
        }
        if (visibleLeft) return true

        var visibleUp = true
        for (i in y+1 until height) {
            if (matrix[x]!![i]!! >= tree) {
                visibleUp = false
                break
            }
        }
        if (visibleUp) return true

        var visibleDown = true
        for (i in 0 until y) {
            if (matrix[x]!![i]!! >= tree) {
                visibleDown = false
                break
            }
        }
        if (visibleDown) return true
        return false
    }

    fun countVisible(): Int {
        var count = 0
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (isVisible(i, j, matrix[i]!![j]!!)) {
                    //println("is visible ${matrix[i]!![j]!!} [${i+1},${j+1}]")
                    count++
                }
            }
        }
        return count
    }

    fun calculateScore(x: Int ,y:Int, tree: Int): Int {
        return calculateRight(x, y, tree) * calculateLeft(x, y, tree) * calculateUp(y, x, tree) * calculateDown(y, x, tree)
    }

    private fun calculateDown(y: Int, x: Int, tree: Int): Int {
        var score = 0
        for (i in y - 1 downTo 0) {
            if (matrix[x]!![i]!! < tree) {
                score++
            } else {
                score++
                break
            }
        }
        return score
    }

    private fun calculateUp(y: Int, x: Int, tree: Int): Int {
        var score = 0
        for (i in y + 1 until height) {
            if (matrix[x]!![i]!! < tree) {
                score++
            } else {
                score++
                break
            }
        }
        return score
    }

    private fun calculateLeft(x: Int, y: Int, tree: Int): Int {
        var score = 0
        for (i in x - 1 downTo 0) {
            if (matrix[i]!![y]!! < tree) {
                score++
            } else {
                score++
                break
            }
        }
        return score
    }

    private fun calculateRight(x: Int, y: Int, tree: Int): Int {
        var score = 0
        for (i in x + 1 until width) {
            if (matrix[i]!![y]!! < tree) {
                score++
            }else {
                score++
                break
            }
        }
        return score
    }

    fun calculateScenicScore(): Int {
        val scores = mutableListOf<Int>()
        for (i in 0 until height) {
            for (j in 0 until width) {
                //println("scenic score: ${matrix[i]!![j]!!} [${i+1},${j+1} ${calculateScore(i,j,matrix[i]!![j]!!)}")
                scores.add(calculateScore(i,j,matrix[i]!![j]!!))
            }
        }
        return scores.max()
    }

    fun printMatrix() {
        matrix.forEach { println(it!!.joinToString { it.toString() }) }
    }
}
