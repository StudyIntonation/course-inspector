package org.studyintonation.course_inspector.inspector;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FilesExistenceInspectionVisitor implements Visitor {

    private List<String> infoAboutNonexistentFiles = new ArrayList<>();
    private final String pathToCourseDirectory;

    public FilesExistenceInspectionVisitor(String pathToCourseDirectory) {
        this.pathToCourseDirectory = pathToCourseDirectory;
    }

    @Override
    public void visit(Visitable visitable) {
        collectInfoAboutNonexistentFiles(visitable);
        if (infoAboutNonexistentFiles.size() != 0) {
            StringBuilder sb = new StringBuilder();
            infoAboutNonexistentFiles.forEach(name -> sb.append(name).append("\n"));
            throw new FilesNonexistentException(sb.toString());
        }
    }

    private void collectInfoAboutNonexistentFiles(Object object) {
        findSatisfyingFieldsAndDoAction(
                object,
                (fieldOwner, field) -> fieldContainsPathToFile(field),
                (fieldOwner, field) -> {
                    try {
                        field.setAccessible(true);
                        String pathToFile = (String) field.get(fieldOwner);
                        File file = new File(pathToCourseDirectory + "/" + pathToFile);
                        if (!file.exists() || file.isDirectory()) {
                            infoAboutNonexistentFiles.add(
                                    fieldOwner.getClass().getSimpleName() +
                                            "." + field.getName() + ": " +
                                            file.getAbsolutePath()
                            );
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private boolean fieldContainsPathToFile(Field field) {
        return fieldNameIsOneOf(field.getName(), "logo", "sound", "pitch", "textMarkup");
    }

}
