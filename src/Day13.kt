fun main() {
    data class Cell(val x: Int, val y: Int)

    fun Cell.foldX(foldX: Int): Cell? {
        if (this.x == foldX) return null
        if (this.x < foldX) return this
        return Cell(foldX - (this.x - foldX), this.y)
    }

    fun Cell.foldY(foldY: Int): Cell? {
        if (this.y == foldY) return null
        if (this.y < foldY) return this
        return Cell(this.x, foldY - (this.y - foldY))
    }

    fun String.parseCell(): Cell {
        val segments = this.split(",").map { it.toInt() }
        return Cell(segments[0], segments[1])
    }

    fun part1(input: List<String>): Int {
        var cells = mutableSetOf<Cell>()
        var folding = false
        for (line in input) {
            if (line.isEmpty()) {
                folding = true
                continue
            }
            if (folding) {
                val segments = line.split("=")
                val fold = segments[1].toInt()
                cells = if (segments[0].contains("x")) cells.mapNotNull { it.foldX(fold) }.toMutableSet()
                else cells.mapNotNull { it.foldY(fold) }.toMutableSet()
                break
            } else {
                cells.add(line.parseCell())
            }
        }
        return cells.size
    }

    fun part2(input: List<String>): Unit {
        var cells = mutableSetOf<Cell>()
        var folding = false
        for (line in input) {
            if (line.isEmpty()) {
                folding = true
                continue
            }
            if (folding) {
                val segments = line.split("=")
                val fold = segments[1].toInt()
                cells = if (segments[0].contains("x")) cells.mapNotNull { it.foldX(fold) }.toMutableSet()
                else cells.mapNotNull { it.foldY(fold) }.toMutableSet()
            } else {
                cells.add(line.parseCell())
            }
        }
        for (y in 0..cells.maxOf { it.y }) {
            for (x in 0..cells.maxOf { it.x }) {
                print(if (cells.contains(Cell(x, y))) '#' else '.')
            }
            print('\n')
        }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
