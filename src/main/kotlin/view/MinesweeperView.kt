package view

import controller.BoardBasedHexListener
import controller.BoardListener
import model.Board
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Polygon
import model.Cell
import tornadofx.View
import kotlin.math.sqrt

class MinesweeperView : View("Hexa MineSweeper"), BoardListener {
    private val WINDOW_WIDTH = 800.0
    private val WINDOW_HEIGHT = 600.0

    private val numberOfBombs = 10;
    var rows = 9
    var columns = 9


    var marginLeft = 40
    var marginRight = 40
    val board = Board(rows, columns, numberOfBombs, this)
    val listener = BoardBasedHexListener(board)
    override val root = AnchorPane()

    init {
        with(root) {
            minHeight = WINDOW_HEIGHT
            minWidth = WINDOW_WIDTH
            background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
            for (hex in board.hexes.values) {
                val graphicHex = GraphicHex(hex.hexX, hex.hexY)
                graphicHex.setOnMouseClicked { listener.hexClicked(TODO()) }
                children.add(graphicHex)
            }
            //setOnMouseClicked { println("Clicked at (${it.x}, ${it.y})") }

        }
    }

    inner class GraphicHex(private val column: Int, private val row: Int) : Polygon() {
        private val R = 20.0
        private val h = sqrt(R * R * 0.75)
        private val HEX_HEIGHT = 2 * R
        private val HEX_WIDTH = 2 * h

        private var markedAsBomb = false
        private val x = column * HEX_WIDTH + row % 2 * h + marginLeft
        private val y = row * HEX_HEIGHT * 0.75 + marginRight

        init {
            points.addAll(generatePoints())
            fill = Color.LIGHTCYAN
            strokeWidth = 1.0
            stroke = Color.BLACK
            onMouseClicked = EventHandler { e: MouseEvent? ->
                val cell = Cell(column, row)
                println("Clicked: ($column, $row)")
                board.openHex(Cell(column, row))
                fill = if (board.hexes[cell]!!.hasBomb) Color.BLACK
                else {
                    val image = when (board.hexes[cell]!!.minedNeighbors) {
                        0 -> Image("zero.jpg")
                        1 -> Image("one.png")
                        2 -> Image("two.png")
                        3 -> Image("three.png")
                        4 -> Image("four.png")
                        5 -> Image("five.png")
                        6 -> Image("six.png")
                        else -> Image("null")
                    }
                    ImagePattern(image)
                }
            }
        }

        private fun generatePoints() = arrayOf(
            x, y,
            x, y + R,
            x + h, y + R * 1.5,
            x + HEX_WIDTH, y + R,
            x + HEX_WIDTH, y,
            x + h, y - R * 0.5
        )
    }
    //Функция, которая обновляет представление, после взаимодестсвия с моделью
    //Эта функция, вызывается при вызове функции turnMade - метода слушателя модели BoardListener
    //В конструкторе init представление регестрируется как слушатель модели
    fun updateBoard() {
        TODO()
    }

    override fun turnMade(cell: Cell) {
        TODO("Not yet implemented")
    }

}

