package controller

import model.Cell

//view -> model
interface HexListener {
    fun hexClicked(cell: Cell)
}