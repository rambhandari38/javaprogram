package com.project.studentmanagement.controllers.studentController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final StringProperty course;
    private final StringProperty time;
    private final StringProperty location;

    public Course(String course, String time, String location) {
        this.course = new SimpleStringProperty(course);
        this.time = new SimpleStringProperty(time);
        this.location = new SimpleStringProperty(location);
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public StringProperty courseProperty() {
        return course;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }
}
