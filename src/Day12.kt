fun main() {
    class Cave(val name: String, val connectedCaveNames: MutableSet<String>)

    fun MutableMap<String, Cave>.find(name: String): Cave {
        if (this[name] != null) return this[name]!!
        val cave = Cave(name, mutableSetOf())
        this[name] = cave
        return cave
    }

    fun Map<String, Int>.find(name: String): Int {
        return this[name] ?: 0
    }

    fun build(input: List<String>): Map<String, Cave> {
        val caveByName = mutableMapOf<String, Cave>()
        input.forEach {
            val segments = it.split("-")
            val cave1 = caveByName.find(segments[0])
            val cave2 = caveByName.find(segments[1])
            cave1.connectedCaveNames.add(cave2.name)
            cave2.connectedCaveNames.add(cave1.name)
        }
        return caveByName
    }

    fun findWayOutRec(
        caveByName: Map<String, Cave>,
        enter: String,
        visitCount: Map<String, Int>,
        canEnter: (name: String, visitCount: Map<String, Int>) -> Boolean
    ): Int {
        // enter
        if (!canEnter(enter, visitCount)) {
            return 0
        }
        val cave = caveByName[enter]!!
        if (enter == "end") return 1
        // recursive step
        val copyCount = visitCount.toMutableMap()
        copyCount[cave.name] = copyCount.find(enter) + 1
        return cave.connectedCaveNames.sumOf {
            findWayOutRec(caveByName, it, copyCount, canEnter)
        }
    }

    fun part1(input: List<String>): Int {
        return findWayOutRec(build(input), "start", mapOf()) { name, visitCount ->
            name != name.lowercase() || visitCount.find(name) == 0
        }
    }

    fun part2(input: List<String>): Int {
        return findWayOutRec(build(input), "start", mapOf()) { name, visitCount ->
            if (name != name.lowercase()) return@findWayOutRec true
            if (visitCount.find(name) == 0) return@findWayOutRec true
            if (name == "start") return@findWayOutRec false
            return@findWayOutRec visitCount
                .filterKeys { it == it.lowercase() }
                .maxOf { it.value } == 1
        }
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val testInput2 = readInput("Day12_test2")
    check(part1(testInput2) == 19)
    check(part2(testInput2) == 103)

    val testInput3 = readInput("Day12_test3")
    check(part1(testInput3) == 226)
    check(part2(testInput3) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
