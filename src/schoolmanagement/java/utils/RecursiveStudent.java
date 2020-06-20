package schoolmanagement.java.utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecursiveStudent extends RecursiveTreeObject<RecursiveStudent> {
    StringProperty id, studentName, courseEnrolled;

    public RecursiveStudent(String id, String studentName, String courseEnrolled) {
        this.id = new SimpleStringProperty(id);
        this.studentName = new SimpleStringProperty(studentName);
        this.courseEnrolled = new SimpleStringProperty(courseEnrolled);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public StringProperty studentNameProperty() {
        return studentName;
    }

    public String getCourseEnrolled() {
        return courseEnrolled.get();
    }

    public void setCourseEnrolled(String courseEnrolled) {
        this.courseEnrolled.set(courseEnrolled);
    }

    public StringProperty courseEnrolledProperty() {
        return courseEnrolled;
    }
}
