fun main() {
    fun part1(input: List<String>): Int {
        val set = setOf(2, 4, 3, 7)
        return input.sumOf {
            it.split("|")[1].split(" ").filter { set.contains(it.length) }.size
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val numbers = it.split("|").map { it.split(" ") }.flatten().map { it.trim() }.filter { it.length > 1 }
                .map { it.toSet() }.toMutableList()
            val segmentsByNumber = mutableMapOf<Int, Set<Char>>()
            segmentsByNumber[1] = numbers.first { it.size == 2 }
            segmentsByNumber[4] = numbers.first { it.size == 4 }
            segmentsByNumber[7] = numbers.first { it.size == 3 }
            segmentsByNumber[8] = numbers.first { it.size == 7 }
            segmentsByNumber[9] = numbers.first { it.size == 6 && it.containsAll(segmentsByNumber[4]!!) }
            segmentsByNumber[0] = numbers.first { it.size == 6 && it.containsAll(segmentsByNumber[1]!!) && !it.containsAll(segmentsByNumber[9]!!) }
            segmentsByNumber[6] = numbers.first { it.size == 6 && !it.containsAll(segmentsByNumber[9]!!) && !it.containsAll(segmentsByNumber[0]!!) }
            segmentsByNumber[3] = numbers.first { it.size == 5 && it.containsAll(segmentsByNumber[1]!!) }
            segmentsByNumber[5] = numbers.first { it.size == 5 && !it.containsAll(segmentsByNumber[3]!!) && segmentsByNumber[6]!!.containsAll(it) }
            segmentsByNumber[2] = numbers.first { it.size == 5 && !it.containsAll(segmentsByNumber[3]!!) && !it.containsAll(segmentsByNumber[5]!!) }

            val numberBySegments = segmentsByNumber.map { Pair(it.value, it.key) }.toMap()
            numberBySegments[numbers.removeLast()]!! +
                    numberBySegments[numbers.removeLast()]!! * 10 +
                    numberBySegments[numbers.removeLast()]!! * 100 +
                    numberBySegments[numbers.removeLast()]!! * 1000
        }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
