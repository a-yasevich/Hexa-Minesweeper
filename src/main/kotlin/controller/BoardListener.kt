package controller

import model.Cell

//model -> view
interface BoardListener {
    fun turnMade(cell: Cell, minedNeighbours: Int)
}