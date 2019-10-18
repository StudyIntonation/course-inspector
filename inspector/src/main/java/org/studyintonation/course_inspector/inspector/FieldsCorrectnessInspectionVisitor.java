package org.studyintonation.course_inspector.inspector;

import java.lang.reflect.Field;

//TODO:
class FieldsCorrectnessInspectionVisitor implements Visitor {
    @Override
    public void visit(Visitable visitable) {
        findSatisfyingFieldsAndDoAction(
                visitable,
                this::fieldIsText,
                this::validate
        );
    }

    private boolean fieldIsText(Object fieldOwner, Field field) {
        return fieldNameIsOneOf(field.getName(), "title", "description", "instructions", "text");
    }

    private void validate(Object fieldOwner, Field field) {
        field.setAccessible(true);
        String text = null;
        try {
            text = (String) field.get(fieldOwner);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (!text.matches("\\w+")) {
            throw new RuntimeException();
        }
    }
}
