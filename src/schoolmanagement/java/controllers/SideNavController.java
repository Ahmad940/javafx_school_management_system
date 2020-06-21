package schoolmanagement.java.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.FxmlHandlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideNavController {
    StackPane homePane, addPane, viewPane;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton viewBtn;

    @FXML
    public void initialize() {
        try {
            homePane = FXMLLoader.load(getClass().getResource(Directories.HOME_FXML_DIR));
            addPane = FXMLLoader.load(getClass().getResource(Directories.ADD_STUDENT_FXML_DIR));
            viewPane = FXMLLoader.load(getClass().getResource(Directories.VIEW_STUDENT_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(SideNavController.class.getName()).log(Level.SEVERE, "Could not load fxml files", e);
            e.printStackTrace();
        }

        handlers();
    }


    public void homeController() {
        FxmlHandlers.animatedFxmlLoader(homeBtn, homePane, homePane.translateXProperty());
    }

    public void handlers() {
        homeBtn.setOnMouseClicked(event -> FxmlHandlers.animatedFxmlLoader(homeBtn, homePane, homePane.translateXProperty()));

        addBtn.setOnMouseClicked(event -> FxmlHandlers.animatedFxmlLoader(addBtn, addPane, addPane.translateYProperty()));

        viewBtn.setOnMouseClicked(event -> FxmlHandlers.animatedFxmlLoader(viewBtn, viewPane, viewPane.translateYProperty()));
    }
}
