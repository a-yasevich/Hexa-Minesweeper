package view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import tornadofx.*

class GameSettingsDialog : Dialog<ButtonType>() {
    //Используем java fx свойства, чтобы понять, какие кнопки были выбраны
    val difficulty = SimpleStringProperty()

    //val yellowComputer: Boolean get() = yellowPlayer.value == "Computer"
    init {
        title = "Hexa Minesweeper"
        with(dialogPane) {
            headerText = "Choose difficulty"
            //Задаём, какие кнопки есть в ButtonBar'e, который есть у каждого диалога
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