package org.studyintonation.course_inspector.cli_utility;

import java.util.Arrays;

public final class CLIUtility {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 3) {
            Help.printArgumentsAreWrong();
            Help.printInfo();
            return;
        }
        if (args.length == 1) {
            if ("-help".equals(args[0])) {
                Help.printInfo();
                return;
            }
            CourseInspection.inspectCourse(args);
        } else {
            String[] nextArgs = Arrays.copyOfRange(args, 1, args.length);
            if ("-info".equals(args[0])) {
                InfoJsonGeneration.generateInfoJson(nextArgs);
            } else {
                Help.printArgumentsAreWrong();
                Help.printInfo();
            }
        }
    }
}
