package model

interface BombPlanter {
    fun plantBombs(numberOfBombs: Int): Set<Cell>
}