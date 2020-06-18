package schoolmanagement.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.UsersDao;
import schoolmanagement.java.main.Launcher;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.utils.Directories;

import java.io.IOException;

public class LoginController {
    private static LoginController instance;
    @FXML
    private VBox detailsPane;
    @FXML
    private HBox loaderPane;
    @FXML
    private StackPane mainPane;
    @FXML
    private JFXTextField userNameTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton signUpBtn;
    private ApplicationContext applicationContext;
    private UsersDao usersDao;

    private double xOffset = 0;
    private double yOffset = 0;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    public String getUsername() {
        return userNameTextField.getText();
    }

    @FXML
    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        usersDao = (UsersDao) applicationContext.getBean("usersDao");

        validators();

        makeStageDraggable();
    }

    private void validators() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

        Image icon = new Image("/schoolmanagement/resource/image/icons8_cancel_16px.png");

        requiredFieldValidator.setMessage("Input Required");
        requiredFieldValidator.setIcon(new ImageView(icon));

        userNameTextField.getValidators().addAll(requiredFieldValidator);
        passwordTextField.getValidators().addAll(requiredFieldValidator);

        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) userNameTextField.validate();
        });

        passwordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) passwordTextField.validate();
        });
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
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleLoginBtn(new ActionEvent());
        }
    }

    @FXML
    public void handleLoginBtn(ActionEvent event) {
        String uName = userNameTextField.getText();
        String pass = DigestUtils.sha1Hex(passwordTextField.getText());

        if (userNameTextField.getText().isEmpty() || userNameTextField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxBluredAlert(loginBtn, detailsPane, "Error", "User name field cannot be empty");
            return;
        }

        if (passwordTextField.getText().isEmpty() || passwordTextField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxBluredAlert(loginBtn, detailsPane, "Error", "Password field cannot be empty");
            return;
        }

        if (usersDao.userExist(uName, pass)) {
            FadeTransition transition = new FadeTransition(Duration.seconds(3), loaderPane);
            loaderPane.setVisible(true);
            transition.setToValue(1);
            transition.setFromValue(0);
            transition.setOnFinished(event1 -> {
                loaderPane.setVisible(false);

                Parent pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource(Directories.MAIN_FXML_DIR));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                Node node = (Node) event.getSource();

                Stage stage = (Stage) node.getScene().getWindow();

                stage.close();
                Scene scene = new Scene(pane);
                scene.getStylesheets().add(getClass().getResource(Directories.JFOENIX_CSS_DIR).toExternalForm());
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            });
            transition.play();
        } else {
            Alerts.INSTANCE.jfxBluredAlert(loginBtn, detailsPane, "Login Error", "Invalid username or password");
        }


    }

    @FXML
    public void handleSignUpBtn(ActionEvent event) {
        StackPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource(Directories.SIGNUP_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.show();
    }
}
