package model

import controller.BoardListener

class Board(
    private val rows: Int,
    private val columns: Int,
    private val numberOfBombs: Int,
    private val bombPlanter: BombPlanter,
    private val listener: BoardListener?
) {
    private var hexes = mapOf<Cell, Hex>()
    private var gameInProcess = true

    init {
        initialize()
    }

    fun openHex(cell: Cell) {
        val hex = hexes[cell] ?: return
        if (hex.hasBomb) {
            gameInProcess = false
            listener?.turnMade(cell, -1)
            return
        }
        if (hex.isOpened)
            return
        hex.isOpened = true
        var minedNeighbours = 0
        for (neighbour in hex.neighbors)
            if (neighbour.isInside() && hexes[neighbour]!!.hasBomb)
                minedNeighbours++
        if (minedNeighbours == 0)
            for (neighbour in hex.neighbors)
                if (neighbour.isInside() && !hexes[neighbour]!!.isOpened)
                    openHex(neighbour)
        listener?.turnMade(cell, minedNeighbours)
    }

    private fun initialize() {
        val hexes = mutableMapOf<Cell, Hex>()
        val bombs = bombPlanter.plantBombs(numberOfBombs)
        for (x in 0 until columns)
            for (y in 0 until rows) {
                val cell = Cell(x, y)
                val hasBomb = bombs.contains(cell)
                hexes[cell] = Hex(x, y, hasBomb)
            }
        this.hexes = hexes
    }

    fun numberOfCellsRemaining() = rows * columns - numberOfBombs - hexes.values.count { it.isOpened }
    fun isGameInProcess() = gameInProcess

    fun clear() {
        if (!gameInProcess)
            gameInProcess = true
        initialize()
    }

    private fun Cell.isInside() = this.hexY in 0 until rows && this.hexX in 0 until columns
}

