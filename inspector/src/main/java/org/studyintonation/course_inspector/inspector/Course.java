package org.studyintonation.course_inspector.inspector;

import java.util.List;

public final class Course implements Visitable {
    private String id;
    private String title;
    private String description;
    private String logo;
    private String difficulty;
    private String category;
    private List<Lesson> lessons;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
