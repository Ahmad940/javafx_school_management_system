package schoolmanagement.java.controllers;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.DepartmentsDao;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.models.Departments;
import schoolmanagement.java.models.Students;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.Formatter;
import schoolmanagement.java.utils.Validators;

import java.util.Date;

public class AddStudentController {
    String getGender = null;
    String getLevel = null;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Label progressLabel;
    @FXML
    private JFXTextField balanceField;
    @FXML
    private JFXTextField amountField;
    @FXML
    private JFXTextField courseNameField;
    @FXML
    private JFXComboBox<Departments> departmentsComboBox;
    @FXML
    private JFXRadioButton certificateRadio;
    @FXML
    private JFXRadioButton diplomaRadio;
    @FXML
    private ToggleGroup level;
    @FXML
    private JFXRadioButton degreeRadio;
    @FXML
    private TextField registrationDateField;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private JFXTextField locationField;
    @FXML
    private JFXTextField emailAddressField;
    @FXML
    private JFXTextField mobileNoField;

    @FXML
    private AnchorPane secondaryPane;

    @FXML
    private StackPane mainPane;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField firstNameField;

    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;
    private DepartmentsDao departmentsDao;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");
        departmentsDao = (DepartmentsDao) applicationContext.getBean("departmentsDao");

        setValues();
    }

    private void setValues() {
        registrationDateField.setText(new Date().toString());

        ObservableList<Departments> departmentsList = FXCollections.observableArrayList(departmentsDao.getDepartments());
        departmentsComboBox.setItems(departmentsList);
    }

    public void onClear() {
        firstNameField.clear();
        lastNameField.clear();
        mobileNoField.clear();
        emailAddressField.clear();
        locationField.clear();
        gender.getToggles().clear();
        registrationDateField.setText(Formatter.INSTANCE.dateTimeFormatOfNow());
        departmentsComboBox.getSelectionModel().clearSelection();
        courseNameField.clear();
        amountField.clear();

        balanceField.setText("N 0.0");
    }

    public void onSave() {
        if (firstNameField.getText().isEmpty() || firstNameField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "First Name Field cannot be empty");
            return;
        }

        if (lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Last Name Field cannot be empty");
            return;
        }

        if (emailAddressField.getText().isEmpty() || emailAddressField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Email Address Field cannot be empty");
            return;
        }

        if (mobileNoField.getText().isEmpty() || mobileNoField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Mobile Number Field cannot be empty");
            return;
        }

        if (locationField.getText().isEmpty() || locationField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Email Address Field cannot be empty");
            return;
        }

        if (!maleRadio.isSelected()) {
            if (!femaleRadio.isSelected()) {
                Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Gender must be selected");
                return;
            }
        }

        if (!degreeRadio.isSelected()) {
            if (!diplomaRadio.isSelected()) {
                if (!certificateRadio.isSelected()) {
                    Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Level must be selected");
                    return;
                }
            }
        }

        if (courseNameField.getText().isEmpty() || courseNameField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Course Name Field cannot be empty");
            return;
        }

        if (departmentsComboBox.getSelectionModel().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Department must be selected");
            return;
        }

        if (amountField.getText().isEmpty() || amountField.getText().trim().isEmpty()) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Course Name Field cannot be empty");
            return;
        }

        if (amountField.getText().length() > 6) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Amount Field must not exceed six characters");
            return;
        }

        if (!Validators.INSTANCE.isValidEmail(emailAddressField.getText())) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Invalid Email Format");
            return;
        }

        if (!Validators.INSTANCE.isNumber(mobileNoField.getText())) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Mobile Number Field must be an Integer");
            return;
        }

        if (Integer.parseInt(amountField.getText()) > 200_000) {
            Alerts.INSTANCE.jfxAlert(saveBtn, "Error", "Amount must not exceed N200 000");
            return;
        }

        if (maleRadio.isSelected()) getGender = "Male";
        if (femaleRadio.isSelected()) getGender = "Female";

        if (degreeRadio.isSelected()) getLevel = "Degree";
        if (diplomaRadio.isSelected()) getLevel = "Diploma";
        if (certificateRadio.isSelected()) getLevel = "Certificate";

        if (!studentsDao.saveStudent(students())) {
            Alerts.INSTANCE.jfxBluredAlert(saveBtn, secondaryPane, "Success", "Student data successfully saved");
            onClear();
        }
    }

    private Students students() {
        Students students = new Students();

        students.setId(null);
        students.setFirstName(firstNameField.getText());
        students.setLastName(lastNameField.getText());
        students.setMobileNumber(mobileNoField.getText());
        students.setEmail(emailAddressField.getText());
        students.setLocation(locationField.getText());
        students.setGender(getGender);
        students.setRegistrationDate(Formatter.INSTANCE.dateTimeFormatOfNow());
        students.setLevel(getLevel);
        students.setDepartment(departmentsComboBox.getSelectionModel().getSelectedItem().toString());
        students.setCourseName(courseNameField.getText());
        students.setAmount(amountField.getText());
        students.setBalance(balanceField.getText());


        return students;
    }

    public void calculateAmount(KeyEvent event) {
        int amount = Integer.parseInt(amountField.getText());
        int calc = 200_000 - amount;
        String value = String.valueOf(calc);

        String balance = "N " + value + ".00";
        balanceField.setText(balance);
    }

}
