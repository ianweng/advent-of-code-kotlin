fun main() {
    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""
        for (i in 0 until input[0].length) {
            val groupBy = input.groupBy { it[i] }
            if ((groupBy['1']?.size ?: 0) >= (groupBy['0']?.size ?: 0)) {
                gamma += '1'
                epsilon += '0'
            } else {
                gamma += '0'
                epsilon += '1'
            }
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var generator: List<String> = input
        var scrubber: List<String> = input

        var i = 0
        while (generator.size != 1) {
            val groupBy = generator.groupBy { it[i] }
            i++
            if (i == input[0].length) i = 0
            if ((groupBy['1']?.size ?: 0) >= (groupBy['0']?.size ?: 0)) {
                generator = groupBy['1']!!
            } else {
                generator = groupBy['0']!!
            }
        }

        i = 0
        while (scrubber.size != 1) {
            val groupBy = scrubber.groupBy { it[i] }
            i++
            if (i == input[0].length) i = 0
            if ((groupBy['1']?.size ?: 0) >= (groupBy['0']?.size ?: 0)) {
                scrubber = groupBy['0']!!
            } else {
                scrubber = groupBy['1']!!
            }
        }

        return generator[0].toInt(2) * scrubber[0].toInt(2)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
