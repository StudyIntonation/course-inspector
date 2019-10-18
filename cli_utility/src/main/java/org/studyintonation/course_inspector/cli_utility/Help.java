package org.studyintonation.course_inspector.cli_utility;

import java.io.File;

final class Help {
    private static final String DEVELOPERS = "antonlamtev@gmail.com";

    private Help() {
        throw new RuntimeException();
    }

    static void printArgumentsAreWrong() {
        System.out.println("Arguments are wrong");
    }

    static void printInfo() {
        String sl = File.separator;

        final String info =
                "Usage:  inspector path" + sl + "to" + sl + "course" + sl + "directory\n" +
                "        (to inspect course)\n\n" +

                "   or:  inspector -info path" + sl + "to" + sl + "directory" + sl + "with" + sl + "courses " + "tag\n" +
                "        (to zip courses and generate info.json)";

        System.out.println(info);
    }

    static void printNoDefectsFound() {
        System.out.println("No defects found");
    }

    static void printReportToDevelopers(String exceptionMessage) {
        System.out.println("Please report to developers: " + DEVELOPERS + " about it:\n" + exceptionMessage);
    }
}
