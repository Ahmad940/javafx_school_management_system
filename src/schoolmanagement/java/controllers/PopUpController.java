package schoolmanagement.java.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.FxmlHandlers;

import java.io.IOException;

public class PopUpController {
    public void exitHandler() {
        Platform.exit();
    }

    public void logoutHandler(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(FxmlHandlers.class.getResource(Directories.LOGIN_FXML_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();

        Stage stage = (Stage) MainController.contentController.getScene().getWindow();
//
        stage.close();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
