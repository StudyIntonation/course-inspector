package org.studyintonation.course_inspector.inspector;

public class TextMarkup implements Visitable {

    private String fragment;
    private String start;
    private String stop;
    private String catchword;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
