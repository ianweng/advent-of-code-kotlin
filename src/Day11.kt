import kotlin.math.max
import kotlin.math.min

fun main() {
    fun List<String>.parse(): List<MutableList<Int>> {
        return this.map { line -> line.map { c -> Character.getNumericValue(c) }.toMutableList() }
    }

    data class Cell(val row: Int, val col: Int)

    fun List<MutableList<Int>>.runStep(): Int {
        // initial increase
        for (row in this.indices)
            for (col in this[row].indices)
                this[row][col]++

        val flashed = mutableSetOf<Cell>()
        var continueFlash = true
        while (continueFlash) {
            continueFlash = false
            for (row in this.indices) {
                for (col in this[row].indices) {
                    val cell = Cell(row, col)
                    if (this[row][col] > 9 && flashed.add(cell)) {
                        continueFlash = true
                        for (r in max(row - 1, 0)..min(row + 1, 9))
                            for (c in max(col - 1, 0)..min(col + 1, 9))
                                this[r][c]++
                    }
                }
            }
        }

        for (row in this.indices)
            for (col in this[row].indices)
                if (this[row][col] > 9)
                    this[row][col] = 0

        return flashed.size
    }

    fun part1(input: List<String>, steps: Int): Int {
        val octopuses = input.parse()
        return (0 until steps).sumOf {
            octopuses.runStep()
        }
    }

    fun part2(input: List<String>): Int {
        val octopuses = input.parse()
        var stepCount = 1
        while (octopuses.runStep() != 100) {
            stepCount++
        }
        return stepCount
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput, 100) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input, 100))
    println(part2(input))
}
