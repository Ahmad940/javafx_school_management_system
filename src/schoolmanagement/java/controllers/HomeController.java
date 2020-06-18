package schoolmanagement.java.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.dao.UsersDao;
import schoolmanagement.java.models.Users;
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
    private AnchorPane mainPane;

    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;
    private UsersDao usersDao;
    private List<Users> userList;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        usersDao = (UsersDao) applicationContext.getBean("usersDao");
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");

        userList = usersDao.getUser(LoginController.getInstance().getUsername());

        setLabel();
    }

    public void setLabel() {
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

    }

    public void onAddDepartment() {

    }
}
