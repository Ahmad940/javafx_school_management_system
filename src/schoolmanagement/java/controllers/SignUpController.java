package schoolmanagement.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import schoolmanagement.java.models.Users;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.Validators;

import java.io.IOException;

public class SignUpController {
    @FXML
    private HBox loaderPane;

    @FXML
    private VBox detailsPane;

    @FXML
    private JFXTextField mobileNoField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private StackPane mainPane;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton signUpBtn;

    private double xOffset = 0;
    private double yOffset = 0;

    private ApplicationContext applicationContext;
    private UsersDao usersDao;

    @FXML
    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        usersDao = (UsersDao) applicationContext.getBean("usersDao");

        validators();
        makeStageDraggable();
    }

    private void validators() {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        NumberValidator numberValidator = new NumberValidator();

        Image icon = new Image("/schoolmanagement/resource/image/icons8_cancel_16px.png");

        requiredFieldValidator.setMessage("Input Required");
        requiredFieldValidator.setIcon(new ImageView(icon));

        numberValidator.setMessage("Integers values only allowed");
        numberValidator.setIcon(new ImageView(icon));

        usernameField.getValidators().addAll(requiredFieldValidator);
        passwordTextField.getValidators().addAll(requiredFieldValidator);
        firstNameField.getValidators().addAll(requiredFieldValidator);
        lastNameField.getValidators().addAll(requiredFieldValidator);
        emailField.getValidators().addAll(requiredFieldValidator);
        mobileNoField.getValidators().addAll(requiredFieldValidator, numberValidator);

        usernameField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) usernameField.validate();
        });

        passwordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) passwordTextField.validate();
        });

        firstNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) firstNameField.validate();
        });

        lastNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) lastNameField.validate();
        });

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) emailField.validate();
        });

        mobileNoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) mobileNoField.validate();
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
            handleSignUpBtn(new ActionEvent());
        }
    }

    @FXML
    public void handleLoginBtn(ActionEvent event) {
        viewHandler(event);
    }

    private void viewHandler(ActionEvent event) {
        StackPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource(Directories.LOGIN_FXML_DIR));
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

    @FXML
    public void handleSignUpBtn(ActionEvent actionEvent) {
        String password = DigestUtils.sha1Hex(passwordTextField.getText());

        if (usernameField.getText().isEmpty() || usernameField.getText().trim().isEmpty() ||
                passwordTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty() ||
                emailField.getText().isEmpty() || emailField.getText().trim().isEmpty() ||
                mobileNoField.getText().isEmpty() || mobileNoField.getText().trim().isEmpty()
        ) {
            Alerts.INSTANCE.jfxBluredAlert(signUpBtn, detailsPane, "Error", "All fields must be filled");
            return;
        }

        if (!Validators.INSTANCE.isValidEmail(emailField.getText())) {
            Alerts.INSTANCE.jfxBluredAlert(signUpBtn, detailsPane, "Error", "Invalid Email");
            return;
        }

//        boolean validate = Validators.INSTANCE.isNumber(mobileNoField.getText());
        if (!Validators.INSTANCE.isNumber(mobileNoField.getText())) {
            Alerts.INSTANCE.jfxBluredAlert(signUpBtn, detailsPane, "Error", "Mobile Number field must be integer");
            return;
        }

        if (usersDao.userExist(usernameField.getText())) {
            Alerts.INSTANCE.jfxBluredAlert(signUpBtn, detailsPane, "error", "User Already exist");
            return;
        }


        boolean userSaved = usersDao.saveUser(new Users(
                null,
                usernameField.getText(), firstNameField.getText(),
                lastNameField.getText(), emailField.getText(),
                mobileNoField.getText(), "staff", password)
        );

        System.out.println(userSaved);

        if (userSaved) {
            Alerts.INSTANCE.jfxBluredAlert(signUpBtn, detailsPane, "Error", "Some errored occur");
            return;
        }

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), loaderPane);
        fadeTransition.setToValue(1);
        fadeTransition.setFromValue(0);

        loaderPane.setVisible(true);

        fadeTransition.setOnFinished(event -> {
            loaderPane.setVisible(false);

            viewHandler(actionEvent);

        });
        fadeTransition.play();

    }

}
