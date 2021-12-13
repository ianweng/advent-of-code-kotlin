fun main() {
    val open = setOf('(', '[', '{', '<')
    val openClose = mapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

    fun part1(input: List<String>): Int {
        val point = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))
        return input.sumOf { line ->
            val opens = mutableListOf<Char>()
            line.forEach {
                if (open.contains(it)) {
                    opens.add(it)
                } else if (openClose[opens.removeLast()] != it) {
                    return@sumOf point[it]!!
                }
            }
            0
        }
    }

    fun part2(input: List<String>): Long {
        val point = mapOf(Pair('(', 1), Pair('[', 2), Pair('{', 3), Pair('<', 4))
        val scores = input
            .map { line ->
                var score = 0L
                val opens = mutableListOf<Char>()
                line.forEach {
                    if (open.contains(it)) {
                        opens.add(it)
                    } else if (openClose[opens.removeLast()] != it) {
                        return@map null
                    }
                }
                opens.reverse()
                opens.forEach { score = score * 5 + point[it]!! }
                score
            }
            .filterNotNull()
            .sorted()
        return scores[scores.size / 2]
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
