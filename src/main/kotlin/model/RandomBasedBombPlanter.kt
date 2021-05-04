package model

import kotlin.random.Random

class RandomBasedBombPlanter : BombPlanter {
    override fun plantBombs(rows:Int, columns: Int, numberOfBombs: Int, exceptionCell: Cell): Set<Cell> {
        val bombs = mutableSetOf<Cell>()
        val random = Random(System.currentTimeMillis())
        var bombsPlanted = 0
        while (bombsPlanted != numberOfBombs) {
            var cell: Cell
            do {
                cell = Cell(random.nextInt(columns), random.nextInt(rows))
            } while (bombs.contains(cell) || cell == exceptionCell)
            bombs.add(cell)
            bombsPlanted++
        }
        return bombs
    }
}