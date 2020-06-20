package schoolmanagement.java.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.models.Students;
import schoolmanagement.java.utils.Directories;
import schoolmanagement.java.utils.RecursiveStudent;

import java.util.List;

public class ViewStudentsController {
    ObservableList<RecursiveStudent> list = null;
    List<Students> studentsList = null;
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

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");

        list = FXCollections.observableArrayList();
        studentsList = studentsDao.getAllStudents();
        setTable();


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
}