import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<Int>): Int {
        // TODO https://en.wikipedia.org/wiki/Median_of_medians is more efficient to find the medium
        // TODO to be 100% correct, would have two mediums when the amount is even
        val medium = input.sorted()[input.size/2]
        return input.sumOf { (it - medium).absoluteValue }
    }

    fun part2(input: List<Int>): Int {
        // could be more efficient :thinking_face:
        val min = input.minOf { it }
        val max = input.maxOf { it }
        return (min..max)
                .map { v ->
                    input
                            .map { (it - v).absoluteValue }
                            .sumOf { (it + 1) * it / 2 }
                }
                .minOf { it }
    }

    val testInput = readInput("Day07_test")[0].split(",").map { it.toInt() }
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")[0].split(",").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
