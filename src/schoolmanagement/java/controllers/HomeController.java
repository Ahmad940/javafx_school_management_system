package schoolmanagement.java.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.util.List;

public class HomeController {
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

    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;
    private UsersDao usersDao;
    private DepartmentsDao departmentsDao;
    private List<Users> userList;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        usersDao = (UsersDao) applicationContext.getBean("usersDao");
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");
        departmentsDao = (DepartmentsDao) applicationContext.getBean("departmentsDao");

        userList = usersDao.getUser(LoginController.getInstance().getUsername());

        setLabels();
        departmentsList();
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
            usernameLabel.setText(user.getUserName());
            roleLabel.setText(user.getRole());
            fullNameLabel.setText(user.getFirstName() + " " + user.getLastName());
            emailLabel.setText(user.getEmail());
            mobileNoLabel.setText(user.getMobileNumber());
        });
    }

    public void onEditUser() {
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(mainPane, content, JFXDialog.DialogTransition.TOP);
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

}
