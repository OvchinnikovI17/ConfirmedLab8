package view

import ProxyTokenizator
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import org.koin.core.component.KoinComponent
import tornadofx.*

class ExitServerView: View("BebraView"), KoinComponent {

    private val result = SimpleStringProperty("")
    val proxyT = ProxyTokenizator()

    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        style {
            backgroundColor += Color.DARKGRAY
        }
        vbox {
            hbox {
                label("Exit Server") {
                    textFill = Color.BLACK
                    style {
                        fontSize = 20.px
                    }
                }
            }
        }
        hbox{
            label(result)
        }
        vbox(3, Pos.TOP_LEFT) {
            prefHeight = 160.0
            hbox(5) {
                button("Назад") {
                    prefWidth = 120.0
                    prefHeight = 35.0
                    style {
                        backgroundColor += Color.web("#37B6CE")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        replaceWith<WorkingPage>()
                    }
                }
                button("Execute") {
                    prefWidth = 120.0
                    prefHeight = 35.0
                    style {
                        backgroundColor += Color.web("#37B6CE")
                        backgroundRadius += box(70.px)
                    }
                    action {
                        sender()
                    }
                }
            }
        }
    }

    private fun sender(){
        val list = mutableListOf<Any>()
        val getResult = proxyT.proxyTokenizator("exit_server", list, BasicInfo.token)
        result.set(getResult)
    }

}