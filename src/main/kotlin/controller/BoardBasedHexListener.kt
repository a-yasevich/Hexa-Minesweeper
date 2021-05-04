package controller

import model.Board
import model.Cell

class BoardBasedHexListener(private val board: Board) : HexListener {
    override fun hexClicked(cell: Cell) {
        board.openHex(cell)
    }
}