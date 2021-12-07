fun main() {
    data class Line(val direction: String, var num: Int)

    fun String.parse(): Line {
        val segments = this.split(" ")
        return Line(segments[0], segments[1].toInt())
    }

    fun part1(input: List<Line>): Int {
        var horizontal = 0
        var depth = 0
        input.forEach {
            when (it.direction) {
                "forward" -> horizontal += it.num
                "down" -> depth += it.num
                "up" -> depth -= it.num
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<Line>): Int {
        var horizontal = 0
        var aim = 0
        var depth = 0
        input.forEach {
            when (it.direction) {
                "forward" -> {
                    horizontal += it.num
                    depth += it.num * aim
                }
                "down" -> aim += it.num
                "up" -> aim -= it.num
            }
        }
        return horizontal * depth
    }

    val testInput = readInput("Day02_test").map { it.parse() }
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").map { it.parse() }
    println(part1(input))
    println(part2(input))
}
