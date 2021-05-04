package model

import controller.BoardListener

class Board
    (
    private val rows: Int,
    private val columns: Int,
    private val numberOfBombs: Int,
    private val bombPlanter: BombPlanter,
    private val listener: BoardListener?
) {
    private var hexes = mapOf<Cell, Hex>()
    private var gameStatus = GameStatus.ON_START

    fun openHex(cell: Cell) {
        if (gameStatus == GameStatus.ON_START)
            initialize(cell)
        val hex = hexes[cell] ?: return
        if (hex.hasBomb) {
            gameStatus = GameStatus.LOSE
            listener?.turnMade(cell, HexStatus.EXPLOSION)
            return
        }
        hex.open()
        var minedNeighbours = 0
        for (neighbour in hex.neighbors.filter { it.isInside() })
            if (hexes[neighbour]?.hasBomb ?: return)
                minedNeighbours++
        if (minedNeighbours == 0)
            for (neighbour in hex.neighbors.filter { it.isInside() })
                if (!(hexes[neighbour] ?: return).isOpened())
                    openHex(neighbour)
        if (numberOfCellsRemaining() == 0)
            gameStatus = GameStatus.WON
        listener?.turnMade(cell, HexStatus.statusByNumberOfNeighbours(minedNeighbours))
    }

    private fun initialize(exceptionCell: Cell) {
        val hexes = mutableMapOf<Cell, Hex>()
        val bombs = bombPlanter.plantBombs(rows, columns, numberOfBombs, exceptionCell)
        for (x in 0 until columns)
            for (y in 0 until rows) {
                val cell = Cell(x, y)
                val hasBomb = bombs.contains(cell)
                hexes[cell] = Hex(cell, hasBomb)
            }
        this.hexes = hexes
        gameStatus = GameStatus.IN_PROCESS
    }

    private fun numberOfCellsRemaining() = rows * columns - numberOfBombs - hexes.values.count { it.isOpened() }
    fun getStatus() = gameStatus
    fun clear() {
        gameStatus = GameStatus.ON_START
    }

    private fun Cell.isInside() = this.y in 0 until rows && this.x in 0 until columns
}