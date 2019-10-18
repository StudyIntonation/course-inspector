package org.studyintonation.course_inspector.inspector;

import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Arrays;

//TODO inspect start and stop and refactor

public class TextMarkupInspectionVisitor implements Visitor {

    private final String pathToCourseDirectory;

    public TextMarkupInspectionVisitor(String pathToCourseDirectory) {
        this.pathToCourseDirectory = pathToCourseDirectory;
    }

    @Override
    public void visit(Visitable visitable) {
        findSatisfyingFieldsAndDoAction(
                visitable,
                (fieldOwner, field) -> fieldContainsPathToTextMarkup(field),
                (fieldOwner, field) -> {
                    field.setAccessible(true);
                    try {
                        String pathToTextMarkup = (String) field.get(fieldOwner);

                        TextMarkup[] textMarkups = JsonParser.parseJson(
                                pathToCourseDirectory + "/" + pathToTextMarkup, TextMarkup[].class
                        );
                        Arrays.stream(textMarkups)
                                .forEach(
                                        textMarkup -> textMarkup.accept(new FieldsAvailabilityInspectionVisitor())
                                );
                    } catch (IllegalAccessException | MalformedJsonException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private boolean fieldContainsPathToTextMarkup(Field field) {
        return "textMarkup".equals(field.getName());
    }

}
