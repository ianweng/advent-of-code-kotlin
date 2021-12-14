fun main() {
    fun part(input: List<String>, run: Int): Long {
        val line = input[0]

        var pairCount = mutableMapOf<String, Long>()
        line.windowed(2).forEach {
            pairCount[it] = pairCount.getOrDefault(it, 0) + 1
        }

        val pairToInsert = mutableMapOf<String, Pair<String, String>>()
        for (i in 2 until input.size) {
            val a = input[i][0].toString()
            val b = input[i][1].toString()
            val c = input[i][6].toString()
            pairToInsert[a + b] = Pair(a + c, c + b)
        }

        for (i in 0 until run) {
            val newPairCount = mutableMapOf<String, Long>()
            pairCount.forEach { (k, v) ->
                val p = pairToInsert[k] ?: return@forEach
                newPairCount[p.first] = newPairCount.getOrDefault(p.first, 0) + v
                newPairCount[p.second] = newPairCount.getOrDefault(p.second, 0) + v
            }
            pairCount = newPairCount
        }

        val charCount = mutableMapOf<Char, Long>()
        pairCount.forEach { (k, v) ->
            charCount[k[0]] = charCount.getOrDefault(k[0], 0) + v
            charCount[k[1]] = charCount.getOrDefault(k[1], 0) + v
        }
        // doubled except the first and the last
        charCount[line.first()] = charCount.getOrDefault(line.first(), 0) + 1
        charCount[line.last()] = charCount.getOrDefault(line.last(), 0) + 1

        return (charCount.values.maxOf { it } - charCount.values.minOf { it }) / 2
    }

    val testInput = readInput("Day14_test")
    println(part(testInput, 10))

    val input = readInput("Day14")
    println(part(input, 10))
    println(part(input, 40))
}
