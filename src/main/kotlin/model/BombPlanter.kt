package model

fun interface BombPlanter {
    fun plantBombs(rows: Int, columns: Int, numberOfBombs: Int, exceptionCell: Cell): Set<Cell>
}