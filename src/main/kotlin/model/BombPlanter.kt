package model

import model.Cell

interface BombPlanter {
    fun plantBombs(numberOfBombs: Int): Set<Cell>
}