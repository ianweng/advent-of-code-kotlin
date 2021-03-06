fun main() {
    fun part1(input: List<Int>): Int {
        var result = 0
        for (i in 1 until input.size)
            if (input[i] > input[i - 1])
                result++
        return result
    }

    fun part2(input: List<Int>): Int {
        var result = 0
        for (i in 3 until input.size)
            if (input[i] > input[i - 3])
                result++
        return result
    }

    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}