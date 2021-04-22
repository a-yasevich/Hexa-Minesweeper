package view

import javafx.scene.control.ButtonBar
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch

class MinesweeperApp : App(MinesweeperView::class) {
    var bombs = 10
    var rows = 9
    var columns = 9
    override fun start(stage: Stage) {
        val dialog = GameSettingsDialog()
        val result = dialog.showAndWait()
        if (result.isPresent && result.get().buttonData == ButtonBar.ButtonData.OK_DONE) {
            when (dialog.difficulty.value) {
                "Intermediate" -> {
                    rows = 13
                    columns = 11
                    bombs = 25
                }
                "Expert" -> {
                    rows = 17
                    columns = 15
                    bombs = 50
                }
                "Supreme" -> {
                    rows = 19
                    columns = 17
                    bombs = 100
                }
            }
            super.start(stage)
        }
    }
}

fun main() {
    launch<MinesweeperApp>()
}