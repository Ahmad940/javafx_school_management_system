package schoolmanagement.java.utils

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.effect.BoxBlur
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Modality
import javafx.stage.Stage

object Alerts {
    fun jfxAlert(node: Node, title: String?, body: String?) {
        val alert: JFXAlert<*> = JFXAlert<Any?>(node.scene.window as Stage)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.isOverlayClose = false
        val layout = JFXDialogLayout()
        layout.setHeading(Label(title))
        layout.setBody(Label(body))
        val closeButton = JFXButton("Close")
        closeButton.styleClass.add("dialog-accept")
        closeButton.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.ENTER) {
                alert.hide()
            }
        }
        closeButton.onAction = EventHandler { event1: ActionEvent? -> alert.hideWithAnimation() }
        layout.setActions(closeButton)
        alert.setContent(layout)
        alert.show()
    }

    fun jfxBluredAlert(node: Node, bluredNode: Node, title: String?, body: String?) {
        val blur = BoxBlur(3.0, 3.0, 3)
        val alert: JFXAlert<*> = JFXAlert<Any?>(node.scene.window as Stage)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.isOverlayClose = false
        val layout = JFXDialogLayout()
        layout.setHeading(Label(title))
        layout.setBody(Label(body))
        val closeButton = JFXButton("Close")
        closeButton.styleClass.add("dialog-accept")

        closeButton.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.ENTER) {
                alert.hide()
                bluredNode.effect = null
            }
        }
        closeButton.onAction = EventHandler { event1: ActionEvent? ->
            alert.hideWithAnimation()
            bluredNode.effect = null
        }
        layout.setActions(closeButton)
        alert.setContent(layout)
        bluredNode.effect = blur
//        alert.onCloseRequest = EventHandler<*> { event: Event? -> bluredNode.effect = null }
        alert.onCloseRequest = EventHandler { event: Event? -> bluredNode.effect = null }
        alert.show()
    }
}