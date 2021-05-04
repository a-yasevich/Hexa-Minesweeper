package controller

import model.Cell
import model.HexStatus

//model -> view
interface BoardListener {
    fun turnMade(cell: Cell, status: HexStatus)
}