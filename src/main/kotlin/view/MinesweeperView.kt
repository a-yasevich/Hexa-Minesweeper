package view

import controller.BoardBasedHexListener
import controller.BoardListener
import model.Board
import javafx.geometry.Insets
import javafx.scene.control.ButtonBar
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Polygon
import model.Cell
import model.RandomBasedBombPlanter
import tornadofx.*
import javax.swing.text.Style
import kotlin.math.sqrt

class MinesweeperView : View("Hexa Minesweeper"), BoardListener {
    private val WINDOW_WIDTH = 800.0
    private val WINDOW_HEIGHT = 600.0

    private var numberOfBombs = (app as MinesweeperApp).bombs
    private var rows = (app as MinesweeperApp).rows
    private var columns = (app as MinesweeperApp).columns

    private lateinit var statusLabel: Label
    private var markerPressed = false

    private val board = Board(rows, columns, numberOfBombs, RandomBasedBombPlanter(rows, columns), this)
    private val listener = BoardBasedHexListener(board)
    private val graphicHexes = mutableMapOf<Cell, GraphicHex>()
    override val root = BorderPane()

    init {
        with(root) {
            top {
                vbox {
                    menubar {
                        menu("Game") {
                            item("Restart").action {
                                restartGame()
                            }
                            separator()
                            item("Exit").action {
                                this@MinesweeperView.close()
                            }
                        }
                    }
                    toolbar {
                        togglebutton {
                            isSelected = false
                            graphic = ImageView("marker.png").apply {
                                fitWidth = 16.0
                                fitHeight = 16.0
                            }
                            action {
                                markerPressed = !markerPressed
                                background = if (isSelected) {
                                    Background(
                                        BackgroundFill(
                                            Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY
                                        )
                                    )
                                } else {
                                    Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
                                }
                            }
                        }
                    }
                }
            }
            center {
                minHeight = WINDOW_HEIGHT
                minWidth = WINDOW_WIDTH
                background = Background(BackgroundFill(Styles.BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY))
                for (x in 0 until columns)
                    for (y in 0 until rows) {
                        val cell = Cell(x, y)
                        val hex = GraphicHex(cell)
                        children.add(hex)
                        graphicHexes[cell] = hex
                        hex.setOnMouseClicked {
                            if (board.isGameInProcess()) {
                                if (markerPressed) {
                                    if (!hex.isOpen())
                                        hex.switchMarker()
                                } else
                                    listener.hexClicked(cell)
                            }
                        }
                    }
            }
            bottom {
                statusLabel = label("")
            }
        }
    }

    class GraphicHex(column: Int, row: Int) : Polygon() {
        constructor(cell: Cell) : this(cell.hexX, cell.hexY)

        private val marginLeft = 20
        private val marginTop = 80
        private val R = 20.0
        private val h = sqrt(R * R * 0.75)
        private val HEX_HEIGHT = 2 * R
        private val HEX_WIDTH = 2 * h

        private val x = column * HEX_WIDTH + row % 2 * h + marginLeft
        private val y = row * HEX_HEIGHT * 0.75 + marginTop
        private var marker = false
        private var isOpen = false

        init {
            points.addAll(generatePoints())
            fill = Styles.CELL_COLOR
            strokeWidth = 1.0
            stroke = Styles.CELL_STROKE_COLOR
        }

        fun switchMarker() {
            if (marker) {
                marker = false
                fill = Styles.CELL_COLOR
            } else {
                marker = true
                fill = ImagePattern(Image(Styles.MARKER))
            }
        }

        fun open() {
            isOpen = true
        }

        fun isOpen() = isOpen
        fun initialState() {
            isOpen = false
            marker = false
            fill = Styles.CELL_COLOR
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

    private fun restartGame() {
        for (hex in graphicHexes.values)
            hex.initialState()
        board.clear()
    }

    private fun updateBoardAndStatus(cell: Cell, minedNeighbours: Int) {
        val hex = graphicHexes[cell] ?: return
        val cellsRemaining = board.numberOfCellsRemaining()
        hex.open()
        statusLabel.text = when {
            !board.isGameInProcess() && cellsRemaining != 0 -> "You lose! Restart game to try again"
            cellsRemaining == 0 -> "You won! Restart game to play again"
            else -> "Game in process"
        }
        when (minedNeighbours) {
            0 -> hex.fill = Styles.EMPTY_CELL_COLOR
            1 -> hex.fill = ImagePattern(Image("one.png"))
            2 -> hex.fill = ImagePattern(Image("two.png"))
            3 -> hex.fill = ImagePattern(Image("three.png"))
            4 -> hex.fill = ImagePattern(Image("four.png"))
            5 -> hex.fill = ImagePattern(Image("five.png"))
            6 -> hex.fill = ImagePattern(Image("six.png"))
            else -> hex.fill = ImagePattern(Image("bomb.png"))
        }
    }

    override fun turnMade(cell: Cell, minedNeighbours: Int) {
        updateBoardAndStatus(cell, minedNeighbours)
    }

}

