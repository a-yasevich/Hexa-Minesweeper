package view

import controller.BoardBasedHexListener
import controller.BoardListener
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Polygon
import model.*
import tornadofx.*
import kotlin.math.sqrt

class MinesweeperView : View("Hexa Minesweeper"), BoardListener {
    private val WINDOW_WIDTH = 800.0
    private val WINDOW_HEIGHT = 600.0
    private val myApp = app as MinesweeperApp

    private var numberOfBombs = myApp.bombs
    private var rows = myApp.rows
    private var columns = myApp.columns

    private lateinit var statusLabel: Label
    private var markerPressed = false

    private val board = Board(rows, columns, numberOfBombs, RandomBasedBombPlanter(), this)
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
                            if (board.getStatus() == GameStatus.IN_PROCESS || board.getStatus() == GameStatus.ON_START) {
                                if (markerPressed) {
                                    if (!hex.isOpen())
                                        hex.switchMarker()
                                } else {
                                    if (!hex.isMarked())
                                        listener.hexClicked(cell)
                                }
                            }
                        }
                    }
            }
            bottom {
                statusLabel = label("Click any hex to start")
            }
        }
    }

    class GraphicHex(column: Int, row: Int) : Polygon() {
        constructor(cell: Cell) : this(cell.x, cell.y)

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

        fun isMarked() = marker
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

    override fun turnMade(cell: Cell, status: HexStatus) {
        val hex = graphicHexes[cell] ?: return
        statusLabel.text = when(board.getStatus()){
            GameStatus.WON -> "You won! Restart game to play again"
            GameStatus.LOSE -> "You lose! Restart game to try again"
            GameStatus.IN_PROCESS -> "Game in process"
            GameStatus.ON_START -> "Press any hex to start"
        }
        hex.open()
        hex.fill = when (status) {
            HexStatus.NO_BOMBS_NEARBY -> Styles.EMPTY_CELL_COLOR
            HexStatus.NEAR_ONE_BOMB -> ImagePattern(Image("one.png"))
            HexStatus.NEAR_TWO_BOMBS -> ImagePattern(Image("two.png"))
            HexStatus.NEAR_THREE_BOMBS -> ImagePattern(Image("three.png"))
            HexStatus.NEAR_FOUR_BOMBS -> ImagePattern(Image("four.png"))
            HexStatus.NEAR_FIVE_BOMBS -> ImagePattern(Image("five.png"))
            HexStatus.NEAR_SIX_BOMBS -> ImagePattern(Image("six.png"))
            HexStatus.EXPLOSION -> ImagePattern(Image("bomb.png"))
        }
    }

}

