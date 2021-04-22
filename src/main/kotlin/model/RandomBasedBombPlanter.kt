package model

import kotlin.random.Random

class RandomBasedBombPlanter(private val rows: Int, private val columns: Int) : BombPlanter {
    override fun plantBombs(numberOfBombs: Int): Set<Cell> {
        val bombs = mutableSetOf<Cell>()
        val random = Random(System.currentTimeMillis())
        var bombsPlanted = 0
        while (bombsPlanted != numberOfBombs) {
            var cell: Cell
            do {
                cell = Cell(random.nextInt(columns), random.nextInt(rows))
            } while (bombs.contains(cell))
            bombs.add(cell)
            bombsPlanted++
        }
        return bombs
    }
}