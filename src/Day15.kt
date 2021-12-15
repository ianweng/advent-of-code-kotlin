import java.util.*
import kotlin.math.min

fun main() {
    data class Coordinate(val y: Int, val x: Int)

    fun findNearbyCoordinates(coordinate: Coordinate, ySize: Int, xSize: Int): List<Coordinate> {
        val result = mutableListOf<Coordinate>()
        if (coordinate.y < ySize - 1) {
            result.add(Coordinate(coordinate.y + 1, coordinate.x))
        }
        if (coordinate.x < xSize - 1) {
            result.add(Coordinate(coordinate.y, coordinate.x + 1))
        }
        if (coordinate.y > 0) {
            result.add(Coordinate(coordinate.y - 1, coordinate.x))
        }
        if (coordinate.x > 0) {
            result.add(Coordinate(coordinate.y, coordinate.x - 1))
        }
        return result
    }

    fun run(localRiskLookUp: List<List<Int>>): Int {
        val foundMinTotalRiskToCoordinate = mutableMapOf<Coordinate, Int>()
        foundMinTotalRiskToCoordinate[Coordinate(0, 0)] = localRiskLookUp[0][0]

        for (y in localRiskLookUp.indices) {
            for (x in localRiskLookUp.indices) {
                if (x == 0 && y == 0) continue
                val move = Coordinate(y, x)
                val local = localRiskLookUp[y][x]
                if (x == 0) {
                    foundMinTotalRiskToCoordinate[move] = foundMinTotalRiskToCoordinate[Coordinate(y - 1, 0)]!! + local
                } else if (y == 0) {
                    foundMinTotalRiskToCoordinate[move] = foundMinTotalRiskToCoordinate[Coordinate(0, x - 1)]!! + local
                } else {
                    foundMinTotalRiskToCoordinate[move] = min(
                        foundMinTotalRiskToCoordinate[Coordinate(y, x - 1)]!!,
                        foundMinTotalRiskToCoordinate[Coordinate(y - 1, x)]!!
                    ) + local
                }
            }
        }

        val coordinatesToOptimize =
            PriorityQueue<Coordinate> { a, b ->
                foundMinTotalRiskToCoordinate[a]!! / (a.x + a.y) -
                        foundMinTotalRiskToCoordinate[b]!! / (b.x + b.y)
            }

        coordinatesToOptimize.addAll(foundMinTotalRiskToCoordinate.keys
            .filter {
                (it.x > 1 &&
                        foundMinTotalRiskToCoordinate[Coordinate(it.y, it.x - 1)]!! >
                        foundMinTotalRiskToCoordinate[it]!!) ||
                        (it.y > 1 &&
                                foundMinTotalRiskToCoordinate[Coordinate(it.y - 1, it.x)]!! >
                                foundMinTotalRiskToCoordinate[it]!!)
            })

        while (coordinatesToOptimize.isNotEmpty()) {
//            println("count: " + coordinatesToOptimize.size)
            val next = coordinatesToOptimize.remove()
            for (it in findNearbyCoordinates(next, localRiskLookUp.size, localRiskLookUp[0].size)) {
                val risk = foundMinTotalRiskToCoordinate[next]!! + localRiskLookUp[it.y][it.x]
                if (risk < foundMinTotalRiskToCoordinate[it]!!) {
                    foundMinTotalRiskToCoordinate[it] = risk
                    coordinatesToOptimize.add(it)
                }
            }
        }

        return foundMinTotalRiskToCoordinate[Coordinate(
            localRiskLookUp.size - 1,
            localRiskLookUp[0].size - 1
        )]!! - localRiskLookUp[0][0]
    }

    fun part1(input: List<String>): Int {
        val localRiskLookUp = input.map { it.toCharArray().toList().map { it.toString().toInt() } }
        return run(localRiskLookUp)
    }

    fun part2(input: List<String>): Int {
        val time = System.currentTimeMillis()
        var localRiskLookUp = input.map { it.toCharArray().toList().map { it.toString().toInt() } }

        localRiskLookUp = localRiskLookUp.map { row ->
            row.map { it + 0 } + row.map { it + 1 } + row.map { it + 2 } + row.map { it + 3 } + row.map { it + 4 }
        }
        localRiskLookUp = localRiskLookUp.map { r -> r.map { it + 0 } } +
                localRiskLookUp.map { r -> r.map { it + 1 } } +
                localRiskLookUp.map { r -> r.map { it + 2 } } +
                localRiskLookUp.map { r -> r.map { it + 3 } } +
                localRiskLookUp.map { r -> r.map { it + 4 } }
        localRiskLookUp = localRiskLookUp.map { r ->
            r.map { c -> if (c > 9) c - 9 else c }
        }
        val result = run(localRiskLookUp)
        println("p2 time: " + (System.currentTimeMillis() - time) / 1000f)
        return result
    }

    val testInput = readInput("Day15_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}