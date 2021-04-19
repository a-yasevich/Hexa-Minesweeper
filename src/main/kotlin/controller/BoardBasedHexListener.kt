package controller

import model.Board
import model.Cell

class BoardBasedHexListener(val board: Board):HexListener {
    //Контроллер видит, когда пользователь совершает действие с представлением и передаёт это действие в модель
    override fun hexClicked(cell: Cell) {
        //if (board.winner() == null)
        board.openHex(TODO())
    }

}