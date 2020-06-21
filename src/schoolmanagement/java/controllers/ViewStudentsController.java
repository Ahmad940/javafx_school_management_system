package schoolmanagement.java.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.models.Students;
import schoolmanagement.java.utils.Alerts;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.RecursiveStudent;

import java.util.List;

public class ViewStudentsController {
    ObservableList<RecursiveStudent> list = null;
    List<Students> studentsList = null;
    int id = 0;
    @FXML
    private Button editBtn;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileNoLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label levelLabel;

    @FXML
    private TreeTableColumn<RecursiveStudent, String> courseEnrolledCol;
    @FXML
    private TreeTableColumn<RecursiveStudent, String> studentNamesCol;
    @FXML
    private TreeTableColumn<RecursiveStudent, String> idCol;
    @FXML
    private JFXTreeTableView<RecursiveStudent> treeTableView;
    @FXML
    private JFXButton refreshField;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXRadioButton namesRadioBtn;
    @FXML
    private ToggleGroup filterGroup;
    @FXML
    private JFXRadioButton idRadioBtn;
    @FXML
    private AnchorPane secondaryPane;
    @FXML
    private StackPane mainPane;
    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;
    @FXML
    private Label courseNameLabel;
    @FXML
    private Label paidLabel;
    @FXML
    private Label balanceLabel;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");

        list = FXCollections.observableArrayList();
        studentsList = studentsDao.getAllStudents();
        treeTableView.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                onDelete();
            }
        });
        setTable();

        treeTableView.getSelectionModel().selectFirst();

        treeTableEventHandler();
    }

    private void treeTableEventHandler() {
        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            id = Integer.parseInt(newValue.getValue().getId());
            System.out.println(id);
            setDetails();
        });
    }

    private void setDetails() {
        List<Students> list = studentsDao.getStudent(String.valueOf(id));
        list.forEach(student -> {
            fullNameLabel.setText(student.getFirstName() + " " + student.getLastName());
            emailLabel.setText(student.getEmail());
            mobileNoLabel.setText(student.getMobileNumber());
            locationLabel.setText(student.getLocation());

            departmentLabel.setText(student.getDepartment());
            levelLabel.setText(student.getLevel());
            courseNameLabel.setText(student.getCourseName());

            paidLabel.setText(student.getAmount());
            balanceLabel.setText(student.getBalance());
        });
    }

    private void setTable() {
        list.clear();
        idCol.setCellValueFactory(param -> param.getValue().getValue().idProperty());
        studentNamesCol.setCellValueFactory(param -> param.getValue().getValue().studentNameProperty());
        courseEnrolledCol.setCellValueFactory(param -> param.getValue().getValue().courseEnrolledProperty());


        TreeItem<RecursiveStudent> root = new RecursiveTreeItem<RecursiveStudent>(list, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

        studentsList.forEach(students -> {
            list.addAll(new RecursiveStudent(students.getId(), students.getFirstName() + " " + students.getLastName(), students.getCourseName()));
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                treeTableView.setPredicate(modelTreeItem ->
                        modelTreeItem.getValue().idProperty().getValue().toLowerCase().contains(newValue)
                                | modelTreeItem.getValue().studentNameProperty().getValue().toLowerCase().contains(newValue)));
    }

    public void onRefresh() {
        list.clear();
        List<Students> refreshList = studentsDao.getAllStudents();
        ObservableList<RecursiveStudent> observableList = FXCollections.observableArrayList();
        refreshList.forEach(students -> {
            observableList.addAll(new RecursiveStudent(students.getId(), students.getFirstName() + " " + students.getLastName(), students.getCourseName()));
        });
        list.addAll(observableList);
    }

    public void onEdit() {

        // Ayy lazy loader !!!Ussop
        Alerts.INSTANCE.jfxBluredAlert(editBtn, mainPane, "Lazy exception handler", "Im feeling lazy to add this feature, in case any one using it, just copy the edit dialog box from HomeController and edit it here /n Good Luck");
    }

    public void onDelete() {
        BoxBlur blur = new BoxBlur(3.0, 3.0, 3);
        secondaryPane.setEffect(blur);

        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(mainPane, content, JFXDialog.DialogTransition.TOP);
        content.setAlignment(Pos.CENTER);
        content.setHeading(new Text(""));
        VBox box = new VBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);

        Label text = new Label("Are you sure you to delete this student with id = " + id);

        box.getChildren().addAll(text);
        content.setBody(box);

        JFXButton okBtn = new JFXButton("Ok");
        JFXButton cancelBtn = new JFXButton("Cancel");

        okBtn.getStyleClass().add("dial-btn");
        cancelBtn.getStyleClass().add("dial-btn");


        okBtn.setOnAction(event -> {
            removeItem();
            dialog.close();
        });

        okBtn.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                removeItem();
                dialog.close();
            }
        });

        cancelBtn.setOnAction(event -> {
            dialog.close();
        });

        content.setActions(okBtn, cancelBtn);

        dialog.setOnDialogClosed(event -> {
            secondaryPane.setEffect(null);
        });
        dialog.setLayoutX(100);
        dialog.show();

    }

    private void removeItem() {
        studentsDao.deleteStudent(String.valueOf(id));
        int index = treeTableView.getSelectionModel().getSelectedIndex();
        list.remove(index);
    }
}