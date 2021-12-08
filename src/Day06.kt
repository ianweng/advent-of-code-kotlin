fun main() {
    fun String.parse(): List<Int> {
        return this.split(",").map { it.toInt() }
    }

    fun MutableMap<Int, Long>.advance() {
        val newFishCount = this[0] ?: 0
        (1..8).forEach { this[it - 1] = this[it] ?: 0 }
        this[8] = newFishCount
        this[6] = (this[6] ?: 0) + newFishCount
    }

    fun part(input: List<Int>, run: Int): Long {
        val fishesByState = input.groupBy { it }.mapValues { it.value.size.toLong() }.toMutableMap()
        for (i in 0 until run) fishesByState.advance()
        return fishesByState.values.sum()
    }

    val testInput = readInput("Day06_test")[0].parse()
    check(part(testInput, 18) == 26L)
    check(part(testInput, 80) == 5934L)
    check(part(testInput, 256) == 26984457539L)

    val input = readInput("Day06")[0].parse()
    println(part(input, 80))
    println(part(input, 256))
}

