package org.studyintonation.course_inspector.inspector;

public class FieldsUnavailableException extends RuntimeException {

    private static final String MESSAGE = "Unavailable fields:\n";

    FieldsUnavailableException(String message) {
        super(MESSAGE + message);
    }
}
