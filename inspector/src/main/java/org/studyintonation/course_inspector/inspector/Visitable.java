package org.studyintonation.course_inspector.inspector;

public interface Visitable {
    void accept(Visitor visitor);
}
