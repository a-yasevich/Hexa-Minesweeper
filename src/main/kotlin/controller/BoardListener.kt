package controller

import model.Cell

interface BoardListener {
    fun turnMade(cell: Cell)
}