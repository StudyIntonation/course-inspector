package org.studyintonation.course_inspector.inspector;

public class FilesNonexistentException extends RuntimeException {
    private static final String MESSAGE = "Nonexistent files:\n";

    FilesNonexistentException(String message) {
        super(MESSAGE + message);
    }
}
