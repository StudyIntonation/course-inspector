package org.studyintonation.course_inspector.info_generator;

import java.util.ArrayList;
import java.util.List;

public class Info {
    private String update;
    private List<Course> courses = new ArrayList<>();

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
