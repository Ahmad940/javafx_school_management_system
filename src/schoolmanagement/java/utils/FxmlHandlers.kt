package schoolmanagement.java.utils

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.DoubleProperty
import javafx.scene.Node
import javafx.util.Duration
import schoolmanagement.java.controllers.MainController

object FxmlHandlers {
    @JvmStatic
    fun animatedFxmlLoader(node: Node, pane: Node?, property: DoubleProperty) {
        val scene = node.scene
        //            pane.translateXProperty().set(scene.getWidth());
        property.set(scene.width)
        MainController.contentController.children.clear()
        MainController.contentController.children.add(pane)
        val timeline = Timeline()
        //            KeyValue kv = new KeyValue(pane.translateXProperty(), 0, Interpolator.EASE_IN);
        val kv = KeyValue(property, 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(1.0), kv)
        timeline.keyFrames.add(kf)
        timeline.play()
    }
}
