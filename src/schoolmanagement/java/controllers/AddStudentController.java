package schoolmanagement.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import schoolmanagement.java.dao.StudentsDao;
import schoolmanagement.java.utils.Directories;

public class AddStudentController {
    @FXML
    private AnchorPane secondaryPane;

    @FXML
    private StackPane mainPane;

    private ApplicationContext applicationContext;
    private StudentsDao studentsDao;

    public void initialize() {
        applicationContext = new ClassPathXmlApplicationContext(Directories.CONFIG_XML);
        studentsDao = (StudentsDao) applicationContext.getBean("studentsDao");

        System.out.println();
    }
}
