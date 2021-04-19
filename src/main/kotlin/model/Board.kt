package model

import controller.BoardListener
import kotlin.random.Random

class Board(val rows: Int, val columns: Int, val numberOfBombs: Int, val listener: BoardListener?) {
    val hexes = mutableMapOf<Cell, Hex>()
    var gameInProcess = true

    private val bombPlanter = object : BombPlanter {
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

    init {
        println("model.Board initialisation")
        val bombs = bombPlanter.plantBombs(numberOfBombs)
        //? Сделать так, чтобы plantBombs возвращал Hex'ы с бомбами, а в блоке init{} я бы сделал Hex'ы без бомб
        println(bombs)
        for (x in 0 until columns)
            for (y in 0 until rows) {
                val cell = Cell(x, y)
                val hasBomb = bombs.contains(cell)
                //!bombs.contains Hex(x,y, true) hexes.add(Hex(x,y, false))
                hexes[cell] = Hex(x, y, hasBomb)
            }
    }
    //makeTurn
    fun openHex(cell: Cell) {
        val hex = hexes[cell]!!
        if (hex.hasBomb)
            gameInProcess = false
        hex.isOpened = true
        var minedNeighbours = 0
        for (neighbour in hex.neighbors)
            if (neighbour.isInside() && hexes[neighbour]!!.hasBomb)
                minedNeighbours++;
        if (minedNeighbours == 0)
            for (neighbour in hex.neighbors)
                if (neighbour.isInside())
                    openHex(neighbour)
        //Если у модели(Board) есть слушатель, то будет вызвана его функция turnMade
        //с тем, чтобы слушатель мог по этому поводу что-то сделать
        //В нашем случае слушатель - это окно(представление), которое должно обновить своё содержимое
        //Таким образом модель знает о представление косвенно; оно всего лишь значет, что у неё (модели) есть слушатели,
        //а о маленькой детали, что представление реализует слушателей модель уже не знает
        hex.minedNeighbors = minedNeighbours;
        listener?.turnMade(TODO())//Передать hexes, чтобы модель пробежалась по ним и отрисовала открытые?
    }

    fun Cell.isInside() = this.hexY in 0 until rows && this.hexX in 0 until columns
}

