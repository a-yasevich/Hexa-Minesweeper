package model

class Hex(private val x: Int, private val y: Int, val hasBomb: Boolean) {

    constructor(cell: Cell, hasBomb: Boolean) : this(cell.x, cell.y, hasBomb)

    private var isOpened = false
    val neighbors: List<Cell> by lazy {
        listOf(
            Cell(if (y % 2 == 0) x - 1 else x + 1, y - 1),
            Cell(x, y - 1),
            Cell(x + 1, y),
            Cell(x - 1, y),
            Cell(if (y % 2 == 0) x - 1 else x + 1, y + 1),
            Cell(x, y + 1)
        )
    }

    fun open() {
        isOpened = true
    }

    fun isOpened() = isOpened
}