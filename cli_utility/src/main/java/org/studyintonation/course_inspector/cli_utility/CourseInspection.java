package org.studyintonation.course_inspector.cli_utility;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import org.studyintonation.course_inspector.inspector.*;

import java.io.File;
import java.io.FileNotFoundException;

final class CourseInspection {
    static void inspectCourse(String[] args) {
        String pathToDirectory = args[0];
        String pathToFile = pathToDirectory + "/course.json";
        String absolutePathToDirectory = new File(pathToDirectory).getAbsolutePath();
        try {
            //There is important to accept visitors in this order
            Course course = JsonParser.parseJson(pathToFile, Course.class);
            course.accept(new FieldsAvailabilityInspectionVisitor());
            course.accept(new FilesExistenceInspectionVisitor(absolutePathToDirectory));
            course.accept(new TextMarkupInspectionVisitor(absolutePathToDirectory));
            Help.printNoDefectsFound();
        } catch (FileNotFoundException | FieldsUnavailableException | FilesNonexistentException |
                MalformedJsonException | JsonParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            Help.printReportToDevelopers(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
