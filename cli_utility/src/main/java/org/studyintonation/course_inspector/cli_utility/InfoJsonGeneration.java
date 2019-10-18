package org.studyintonation.course_inspector.cli_utility;

import org.studyintonation.course_inspector.info_generator.InfoJsonGenerator;

import java.io.IOException;

class InfoJsonGeneration {
    static void generateInfoJson(String[] args) {
        InfoJsonGenerator generator = new InfoJsonGenerator();
        System.out.println(args[0] + args[1]);
        try {
            generator.generateInfoJson(args[0], args[1]);
            System.out.println("Generation successfully completed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            Help.printReportToDevelopers(e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
