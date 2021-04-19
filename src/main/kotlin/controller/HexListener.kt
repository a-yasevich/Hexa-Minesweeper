package controller

import model.Cell

interface HexListener {
    fun hexClicked(cell: Cell)
}