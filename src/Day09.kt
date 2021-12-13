fun main() {
    fun List<String>.parse(): List<List<Int>> {
        return this.map { line -> line.map { c -> Character.getNumericValue(c) } }
    }

    fun part1(input: List<String>): Int {
        val locations = input.parse()
        var sum = 0
        for (row in locations.indices) for (col in locations[row].indices) {
            val value = locations[row][col]
            if (row > 0 && value >= locations[row - 1][col]) continue
            if (row + 1 < locations.size && value >= locations[row + 1][col]) continue
            if (col > 0 && value >= locations[row][col - 1]) continue
            if (col + 1 < locations[row].size && value >= locations[row][col + 1]) continue
            sum += value + 1
        }
        return sum
    }

    data class Cell(val row: Int, val col: Int)

    fun List<List<Int>>.discover(discovered: MutableSet<Cell>, cell: Cell) {
        if (!discovered.add(cell)) return

        val up = cell.row - 1
        if (up >= 0 && this[up][cell.col] < 9) {
            this.discover(discovered, Cell(up, cell.col))
        }

        val down = cell.row + 1
        if (down < this.size && this[down][cell.col] < 9) {
            this.discover(discovered, Cell(down, cell.col))
        }

        val left = cell.col - 1
        if (left >= 0 && this[cell.row][left] < 9) {
            this.discover(discovered, Cell(cell.row, left))
        }

        val right = cell.col + 1
        if (right < this[cell.row].size && this[cell.row][right] < 9) {
            this.discover(discovered, Cell(cell.row, right))
        }
    }

    fun part2(input: List<String>): Int {
        val locations = input.parse()
        val counts = mutableListOf<Int>()
        val visited = mutableSetOf<Cell>()
        for (row in locations.indices) for (col in locations[row].indices) {
            val cell = Cell(row, col)
            if (!visited.add(cell) || locations[row][col] == 9) continue
            val basin = mutableSetOf<Cell>()
            locations.discover(basin, cell)
            counts.add(basin.size)
            visited.addAll(basin)
        }
        counts.sort()
        return counts.removeLast() * counts.removeLast() * counts.removeLast()
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
