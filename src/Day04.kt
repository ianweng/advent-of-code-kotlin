fun main() {
    data class Cell(val value: Int, var drawn: Boolean)

    data class Board(val cells: List<Cell>, var win: Boolean)

    data class LineRef(val cellLookUp: MutableMap<Int, Cell>, val board: Board)

    fun String.toCells(): List<Cell> {
        return this.split(" ").filter { it.isNotEmpty() }.map { Cell(it.toInt(), false) }
    }

    fun List<Cell>.toCellLookUp(): MutableMap<Int, Cell> {
        return mutableMapOf(*this.map { Pair(it.value, it) }.toTypedArray())
    }

    fun MutableMap<Int, MutableList<LineRef>>.add(cells: List<Cell>, board: Board) {
        cells.forEach {
            val lineRefs = this[it.value] ?: mutableListOf()
            lineRefs.add(LineRef(cells.toCellLookUp(), board))
            this[it.value] = lineRefs
        }
    }

    fun List<String>.toLineRefsLookUp(): MutableMap<Int, MutableList<LineRef>> {
        val lookUp = mutableMapOf<Int, MutableList<LineRef>>()
        for (i in 2 until this.size step 6) {
            val board = listOf(this[i].toCells(), this[i + 1].toCells(), this[i + 2].toCells(), this[i + 3].toCells(), this[i + 4].toCells())
            val boardCells = Board(board.flatten(), false)

            board.forEach { row ->
                lookUp.add(row, boardCells)
            }
            (0..4).forEach { columnNum ->
                lookUp.add(board.map { it[columnNum] }, boardCells)
            }
        }
        return lookUp
    }

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val lineRefsLookUp = input.toLineRefsLookUp()

        for (num in numbers) {
            for (lineRef in lineRefsLookUp[num] ?: mutableListOf()) {
                lineRef.cellLookUp[num]!!.drawn = true
                if (!lineRef.cellLookUp.values.map { it.drawn }.contains(false)) {
                    return num * lineRef.board.cells.filter { !it.drawn }.sumOf { it.value }
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val lineRefsLookUp = input.toLineRefsLookUp()

        var remainingBoards = input.size / 6
        for (num in numbers) {
            val lineRefs = lineRefsLookUp[num] ?: mutableListOf()
            for (i in lineRefs.size - 1 downTo 0) {
                val lineRef = lineRefs[i]
                if (lineRef.board.win) {
                    lineRefs.removeAt(i)
                    continue
                }
                lineRef.cellLookUp[num]!!.drawn = true
                if (!lineRef.cellLookUp.values.map { it.drawn }.contains(false)) {
                    if (remainingBoards == 1) {
                        return num * lineRef.board.cells.filter { !it.drawn }.sumOf { it.value }
                    } else {
                        lineRef.board.win = true
                        remainingBoards--
                    }
                }
            }
        }

        return -1
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

