package model

class Hex(val hexX: Int, val hexY: Int, val hasBomb: Boolean) {
    var isOpened = false
    var minedNeighbors = -1;
    val neighbors = listOf(
        Cell(if (hexY % 2 == 0) hexX - 1 else hexX + 1, hexY - 1),
        Cell(hexX, hexY - 1),
        Cell(hexX + 1, hexY),
        Cell(hexX - 1, hexY),
        Cell(if (hexY % 2 == 0) hexX - 1 else hexX + 1, hexY + 1),
        Cell(hexX, hexY + 1)
    )

    fun touch() {
        isOpened = true
    }
}