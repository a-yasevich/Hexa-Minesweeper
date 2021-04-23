package view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import tornadofx.*

class GameSettingsDialog : Dialog<ButtonType>() {
    val difficulty = SimpleStringProperty()

    init {
        title = "Hexa Minesweeper"
        with(dialogPane) {
            headerText = "Choose difficulty"
            buttonTypes.add(ButtonType("Start Game", ButtonBar.ButtonData.OK_DONE))
            buttonTypes.add(ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE))
            content = hbox {
                vbox {
                    label("Difficulty")
                    togglegroup {
                        bind(difficulty)
                        radiobutton("Beginner") {
                            isSelected = true
                        }
                        radiobutton("Intermediate")
                        radiobutton("Expert")
                        radiobutton("Supreme")
                    }
                }
            }
        }
    }
}