package model

class Hex(val hexX: Int, val hexY: Int, val hasBomb: Boolean) {
    constructor(cell: Cell, hasBomb: Boolean) : this(cell.hexX, cell.hexY, hasBomb)

    var isOpened = false
    val neighbors = listOf(
        Cell(if (hexY % 2 == 0) hexX - 1 else hexX + 1, hexY - 1),
        Cell(hexX, hexY - 1),
        Cell(hexX + 1, hexY),
        Cell(hexX - 1, hexY),
        Cell(if (hexY % 2 == 0) hexX - 1 else hexX + 1, hexY + 1),
        Cell(hexX, hexY + 1)
    )

    override fun toString(): String {
        return "Hex(hexX=$hexX, hexY=$hexY, hasBomb=$hasBomb)"
    }

}