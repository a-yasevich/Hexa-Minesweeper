package view

import tornadofx.App
import tornadofx.launch

class MinesweeperApp:App(MinesweeperView::class)

fun main() {
    println("I was launched")
    launch<MinesweeperApp>()
}