import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Coordinate(val x: Int, val y: Int)

    data class Line(val start: Coordinate, val end: Coordinate)

    fun String.parse(): Line {
        val segments = this.split(" -> ")
        val startSegments = segments[0].split(",").map { it.toInt() }
        val endSegments = segments[1].split(",").map { it.toInt() }
        return Line(
                Coordinate(startSegments[0], startSegments[1]),
                Coordinate(endSegments[0], endSegments[1]))
    }

    fun MutableMap<Coordinate, Int>.add(coordinate: Coordinate) {
        val count = this[coordinate] ?: 0
        this[coordinate] = count + 1
    }

    infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }

    fun Line.part1Move(): List<Coordinate> {
        if (this.start.x == this.end.x) {
            return (this.start.y toward this.end.y)
                    .map { Coordinate(this.start.x, it) }
        }
        if (this.start.y == this.end.y) {
            return (this.start.x toward this.end.x)
                    .map { Coordinate(it, this.start.y) }
        }
        return listOf()
    }

    fun Line.part2Move(): List<Coordinate> {
        if (this.start.x == this.end.x) {
            return (this.start.y toward this.end.y)
                    .map { Coordinate(this.start.x, it) }
        }
        if (this.start.y == this.end.y) {
            return (this.start.x toward this.end.x)
                    .map { Coordinate(it, this.start.y) }
        }
        val deltaX = (this.start.x - this.end.x).absoluteValue
        val deltaY = (this.start.y - this.end.y).absoluteValue
        if (deltaX == deltaY) {
            val xs = this.start.x toward this.end.x
            val ys = this.start.y toward this.end.y
            return (0..deltaX).map {
                Coordinate(xs.elementAt(it), ys.elementAt(it))
            }
        }
        return listOf()
    }

    fun part1(input: List<String>): Int {
        val overlapLookUp = mutableMapOf<Coordinate, Int>()

        input.map { it.parse() }.forEach {
            it.part1Move().forEach {
                overlapLookUp.add(it)
            }
        }

        return overlapLookUp.values.count { it > 1 }
    }

    fun part2(input: List<String>): Int {
        val overlapLookUp = mutableMapOf<Coordinate, Int>()

        input.map { it.parse() }.forEach {
            it.part2Move().forEach {
                overlapLookUp.add(it)
            }
        }

        return overlapLookUp.values.count { it > 1 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

