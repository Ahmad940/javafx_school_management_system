package schoolmanagement.java.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import schoolmanagement.java.main.Launcher;
import schoolmanagement.java.utils.Directories;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    public static AnchorPane contentController;
    @FXML
    private StackPane mainPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Button popUpBtn;
    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {
//        System.out.println(LoginController.getInstance().getUsername());
        contentController = contentPane;

        Parent homePane = null;
        try {
            homePane = FXMLLoader.load(getClass().getResource(Directories.HOME_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(MainController.class.getName()).log(Level.CONFIG, "Error, unable to load fxml file due to error", e);
            e.getStackTrace();
        }
//        1142
//        Scene scene = contentPane.getScene();
        homePane.translateXProperty().set(1142);

//        contentController.getChildren().clear();
        contentController.getChildren().add(homePane);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(homePane.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

//        contentController.getChildren().add(homePane);

        handlePopup();
        initDrawer();
        makeStageDraggable();
    }

    private void makeStageDraggable() {
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launcher.stage.setX(event.getScreenX() - xOffset);
                Launcher.stage.setY(event.getScreenY() - yOffset);
                Launcher.stage.setOpacity(0.7f);
            }
        });
        mainPane.setOnDragDone((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });
        mainPane.setOnMouseReleased((e) -> {
            Launcher.stage.setOpacity(1.0f);
        });
    }


    @FXML
    public void handlePopup() {
        System.out.println("Clicked");
        VBox popupSource = null;

        try {
            popupSource = FXMLLoader.load(getClass().getResource(Directories.POP_UP_FXML_DIR));
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Could not load fxml file", e);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        JFXPopup popup = new JFXPopup(popupSource);
        popUpBtn.setOnMousePressed(e ->
                popup.show(popUpBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, -75.0, 46.0)
        );
    }

    private void initDrawer() {
        VBox sideNave = null;
        try {
            sideNave = FXMLLoader.load(getClass().getResource(Directories.SIDE_NAV_BAR_FXML_DIR));
            drawer.setSidePane(sideNave);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }
}
