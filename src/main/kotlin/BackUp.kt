import model.Board
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import model.Hex
import tornadofx.View

class BackUp : View("Hexa MineSweeper") {
    private val WINDOW_WIDTH = 800.0
    private val WINDOW_HEIGHT = 600.0

    val numberOfBombs = 10
    val rows = 9
    val columns = 9

    var marginLeft = 40
    var marginRight = 40
    private val board = Board(rows, columns, numberOfBombs, null)
    override val root = AnchorPane()
    //private val hexMap = mutableMapOf<Pair<Int, Int>, ImageHex>()

    init {
        println("I am going to initialize")
        with(root) {
            minHeight = WINDOW_HEIGHT
            minWidth = WINDOW_WIDTH
            background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
            for (hex in board.hexes.values)
                root.children.add(ImageHex(hex))
            setOnMouseClicked { println("Clicked at (${it.x}, ${it.y})") }

        }
    }

    inner class ImageHex(val hex: Hex) : Polygon() {

        private val R = 20.0
        private val h = Math.sqrt(R * R * 0.75)
        private val HEX_HEIGHT = 2 * R
        private val HEX_WIDTH = 2 * h

        private val x = hex.hexX * HEX_WIDTH + hex.hexY % 2 * h + marginLeft
        private val y = hex.hexY * HEX_HEIGHT * 0.75 + marginRight

        init {
            points.addAll(generatePoints())
            fill = Color.LIGHTCYAN
            strokeWidth = 1.0
            stroke = Color.BLACK
            onMouseClicked = EventHandler { e: MouseEvent? ->
                println("Pressed: $this")
                fill = if (hex.hasBomb) Color.BLACK
                else Color.DARKCYAN
                var minedNeighbours = 0
                /*for (neighbour in hex.neighbors) {
                    println("$neighbour isInside? ${neighbour.isInside()}")
                    if (neighbour.isInside() && hexMap[neighbour]!!.hex.hasBomb)
                        minedNeighbours++
                }
                */
                /*if (minedNeighbours == 0)
                    for (neighbour in neighbors)
                        if (neighbour.isInside())
                            hexMap[neighbour]!!.fireEvent(MouseEvent(MouseEvent.MOUSE_CLICKED, 0.0, 0.0, 0.0, 0.0, MouseButton.PRIMARY, 1, false, false, false, false, false, false, false, false, false, false, null))
                 */
                /*  val image = when (minedNeighbours) {
                      0 -> Image("six.jpg")
                      1 -> Image("one.png")
                      2 -> Image("two.png")
                      3 -> Image("three.png")
                      4 -> Image("four.png")
                      5 -> Image("five.png")
                      6 -> Image("six.png")
                      else -> Image("null")
                  }
                  fill = ImagePattern(image)
                 */
            }

        }

        //private fun Pair<Int, Int>.isInside() = this.first in 0 until columns && this.second in 0 until rows
        private fun generatePoints() = arrayOf(
            x, y,
            x, y + R,
            x + h, y + R * 1.5,
            x + HEX_WIDTH, y + R,
            x + HEX_WIDTH, y,
            x + h, y - R * 0.5
        )
    }

}

