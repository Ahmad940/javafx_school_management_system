package schoolmanagement.java.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.DepartmentsDao;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.dao.UsersDao;
import schoolmanagement.java.models.Departments;
import schoolmanagement.java.models.Users;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.RecursiveUser;
import schoolmanagement.java.utils.Validators;

import java.util.List;

public class HomeController {
    String firstName, lastName, fullName = null;
    ObservableList<RecursiveUser> list = null;
    List<Users> userTableViewList = null;
    String username = "";
    @FXML
    private JFXTextField searchField;

    @FXML
    private Button addDepartmentBtn;

    @FXML
    private JFXListView<Label> departmentsListView;

    @FXML
    private Button editUserBtn;

    @FXML
    private Label mobileNoLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label targetCountLabel;

    @FXML
    private Label totalFemaleCountLabel;

    @FXML
    private Label totalMalesCountLabel;

    @FXML
    private Label totalRegisteredCountLabel;

    @FXML
    private StackPane mainPane;
    @FXML
    private AnchorPane secondaryPane;

    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;
    private UsersDao usersDao;
    private DepartmentsDao departmentsDao;
    private List<Users> userList;
    @FXML
    private JFXButton refreshBtn;
    @FXML
    private JFXTreeTableView<RecursiveUser> tableView;
    @FXML
    private TreeTableColumn<RecursiveUser, String> userCol;
    @FXML
    private TreeTableColumn<RecursiveUser, String> emailCol;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        usersDao = (UsersDao) applicationContext.getBean("usersDao");
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");
        departmentsDao = (DepartmentsDao) applicationContext.getBean("departmentsDao");

        userList = usersDao.getUser(LoginController.getInstance().getUsername());
        list = FXCollections.observableArrayList();
        userTableViewList = usersDao.getAllUsers();

        setTable();
        setLabels();
        departmentsList();
    }

    private void setTable() {
        userCol.setCellValueFactory(param -> param.getValue().getValue().userNameProperty());
        emailCol.setCellValueFactory(param -> param.getValue().getValue().emailProperty());

        TreeItem<RecursiveUser> root = new RecursiveTreeItem<RecursiveUser>(list, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
        tableView.setShowRoot(false);

        userTableViewList.forEach(users -> {
            list.addAll(new RecursiveUser(users.getUserName(), users.getEmail()));
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                tableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().userNameProperty().getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().emailProperty().getValue().toLowerCase().contains(newValue)));
    }

    private void departmentsList() {
        departmentsListView.getItems().clear();
        List<Departments> departments = departmentsDao.getDepartments();
        departments.forEach(dept -> {
            Label label = new Label(dept.getDepartment());
            departmentsListView.getItems().add(label);
        });

    }

    public void setLabels() {
        totalRegisteredCountLabel.setText(studentsDao.totalReg().toString());
        totalMalesCountLabel.setText(studentsDao.totalMales().toString());
        totalFemaleCountLabel.setText(studentsDao.totalFemales().toString());

        userList.forEach(user -> {
            firstName = user.getFirstName();
            lastName = user.getLastName();
            fullName = user.getFirstName() + " " + user.getLastName();

            usernameLabel.setText(user.getUserName());
            roleLabel.setText(user.getRole());
            fullNameLabel.setText(fullName);
            emailLabel.setText(user.getEmail());
            mobileNoLabel.setText(user.getMobileNumber());
        });
    }

    public void onEditUser() {
        BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
        secondaryPane.setEffect(blur);

        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(mainPane, content, JFXDialog.DialogTransition.TOP);
//        JFXDialog dialog = new JFXDialog((StackPane) mainPane, content, JFXDialog.DialogTransition.CENTER);
        content.setAlignment(Pos.CENTER);
        content.setHeading(new Text("Add Department"));
        VBox box = new VBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);

        JFXTextField userNameField = new JFXTextField();
        userNameField.setText(usernameLabel.getText());
        userNameField.setPromptText("User Name");
        userNameField.getStyleClass().add("deptField");
        userNameField.setDisable(true);

        JFXTextField firstNameField = new JFXTextField();
        firstNameField.setText(firstName);
        firstNameField.setPromptText("First name");
        firstNameField.getStyleClass().add("deptField");

        JFXTextField lastNameField = new JFXTextField();
        lastNameField.setText(lastName);
        lastNameField.setPromptText("Last name");
        lastNameField.getStyleClass().add("deptField");

        JFXTextField emailField = new JFXTextField();
        emailField.setText(emailLabel.getText());
        emailField.setPromptText("Email Address");
        emailField.getStyleClass().add("deptField");

        JFXTextField mobileNumberField = new JFXTextField();
        mobileNumberField.setText(mobileNoLabel.getText());
        mobileNumberField.setPromptText("Mobile Number");
        mobileNumberField.getStyleClass().add("deptField");

        box.getChildren().addAll(userNameField, firstNameField, lastNameField, emailField, mobileNumberField);
        box.setSpacing(30.0);
        content.setBody(box);

        JFXButton saveBtn = new JFXButton("Save");
        JFXButton cancelBtn = new JFXButton("Cancel");

        saveBtn.getStyleClass().add("dial-btn");
        cancelBtn.getStyleClass().add("dial-btn");


        saveBtn.setOnAction(event -> {
            if (firstNameField.getText().isEmpty() || firstNameField.getText().trim().isEmpty()) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "First Name Field cannot be empty");
                return;
            }

            if (lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty()) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "Last Name Field cannot be empty");
                return;
            }

            if (emailField.getText().isEmpty() || emailField.getText().trim().isEmpty()) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "Email Field cannot be empty");
                return;
            }

            if (mobileNumberField.getText().isEmpty() || mobileNumberField.getText().trim().isEmpty()) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "Email Field cannot be empty");
                return;
            }

            if (!Validators.INSTANCE.isNumber(mobileNumberField.getText())) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "Mobile Number Field Must Be a Number");
                return;
            }

            if (!usersDao.updateUser(userNameField.getText(), firstNameField.getText(), lastNameField.getText(),
                    emailField.getText(), mobileNumberField.getText())) {
                firstName = firstNameField.getText();
                lastName = lastNameField.getText();
                fullName = firstName + " " + lastName;

                fullNameLabel.setText(fullName);
                emailLabel.setText(emailField.getText());
                mobileNumberField.setText(mobileNumberField.getText());
            }

            dialog.close();
            departmentsList();
        });

        cancelBtn.setOnAction(event -> {
            dialog.close();
        });

        content.setActions(saveBtn, cancelBtn);

        dialog.setOnDialogClosed(event -> {
            secondaryPane.setEffect(null);
        });
        dialog.show();
    }

    public void onAddDepartment() {
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(mainPane, content, JFXDialog.DialogTransition.RIGHT);
//        JFXDialog dialog = new JFXDialog((StackPane) mainPane, content, JFXDialog.DialogTransition.CENTER);
        content.setAlignment(Pos.CENTER);
        content.setHeading(new Text("Add Department"));
        VBox box = new VBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        JFXTextField deptNameField = new JFXTextField();
        deptNameField.setPromptText("Enter Department Name");
        deptNameField.getStyleClass().add("deptField");

        box.getChildren().addAll(deptNameField);
        content.setBody(box);

        JFXButton addBtn = new JFXButton("Add");
        JFXButton cancelBtn = new JFXButton("Cancel");

        addBtn.getStyleClass().add("dial-btn");
        cancelBtn.getStyleClass().add("dial-btn");

        addBtn.setOnAction(event -> {
            if (deptNameField.getText().isEmpty() || deptNameField.getText().trim().isEmpty()) {
                Alerts.INSTANCE.jfxAlert(mainPane, "Error", "Department Field cannot be empty");
                return;
            }
            departmentsDao.saveDepartment(deptNameField.getText());
            dialog.close();
            departmentsList();
        });

        cancelBtn.setOnAction(event -> {
            dialog.close();
        });

        content.setActions(addBtn, cancelBtn);

        dialog.setLayoutX(100);
        dialog.show();
    }

    public void refreshDept(ActionEvent event) {
        System.out.println("refresh context button clicked");
        departmentsList();
    }

    public void deleteDept(ActionEvent event) {
        System.out.println("delete context button clicked");
        Label label = departmentsListView.getSelectionModel().getSelectedItem();
        departmentsDao.deleteDepartment(label.getText());
        departmentsList();
    }

    public void onRefresh() {
        list.clear();
        List<Users> refreshList = usersDao.getAllUsers();
        ObservableList<RecursiveUser> observableList = FXCollections.observableArrayList();
        refreshList.forEach(users -> {
            observableList.addAll(new RecursiveUser(users.getUserName(), users.getEmail()));
        });
        list.addAll(observableList);
    }
}
