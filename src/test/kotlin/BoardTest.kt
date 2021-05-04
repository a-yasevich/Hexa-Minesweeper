import model.*
import org.junit.jupiter.api.Test

class BoardTest {
    private val testCell = Cell(5, 5)

    private fun Board.assertStatus(status: GameStatus) = status == getStatus()

    @Test
    fun win() {
        val diagonalPlanter = BombPlanter { rows, columns, _, _ ->
            val bombs = mutableSetOf<Cell>()
            for (row in 0 until rows)
                for (column in 0 until columns)
                    if (row == column)
                        bombs.add(Cell(row, column))
            bombs
        }
        val board = Board(9, 9, 9, diagonalPlanter, null)
        board.assertStatus(GameStatus.ON_START)
        board.openHex(Cell(8, 0))
        board.assertStatus(GameStatus.IN_PROCESS)
        board.openHex(Cell(0, 8))
        board.assertStatus(GameStatus.IN_PROCESS)
        board.openHex(Cell(1, 0))
        board.assertStatus(GameStatus.IN_PROCESS)
        board.openHex(Cell(8, 7))
        board.assertStatus(GameStatus.WON)
    }

    @Test
    fun verySadLoss() {
        val planter = BombPlanter { _: Int, _: Int, _: Int, _: Cell -> setOf(testCell) }
        val board = Board(9, 9, 1, planter, null)

        board.openHex(testCell)
        board.assertStatus(GameStatus.LOSE)
    }

    @Test
    fun boardContainingManyBombsIsCreated() {
        val board = Board(9, 9, 80, RandomBasedBombPlanter(), null)
        board.assertStatus(GameStatus.ON_START)
        board.openHex(testCell)
        board.assertStatus(GameStatus.WON)
    }

    @Test
    fun oneTurnWin() {
        val board = Board(9, 9, 1, RandomBasedBombPlanter(), null)
        board.assertStatus(GameStatus.ON_START)
        board.openHex(testCell)
        board.assertStatus(GameStatus.WON)
    }
}