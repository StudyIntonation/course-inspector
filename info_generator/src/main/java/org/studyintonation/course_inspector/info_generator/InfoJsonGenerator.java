package org.studyintonation.course_inspector.info_generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.studyintonation.course_inspector.inspector.JsonParser;

import java.io.*;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

public class InfoJsonGenerator {
    private static final String LOGO_URL_BASE = "https://github.com/bogach/prof-courses/raw/master/";
    private static final String COURSE_URL_BASE = "https://github.com/bogach/prof-courses/releases/download/";

    private File directoryWithCourses;
    private String tag;
    private Info info = new Info();

    public void generateInfoJson(String pathToDirectoryWithCourses, String tag) throws IOException {
        directoryWithCourses = new File(pathToDirectoryWithCourses);
        if (!directoryWithCourses.exists()) {
            throw new FileNotFoundException("Directory " + pathToDirectoryWithCourses + "does not exist");
        }
        this.tag = tag;
        zipCourses();
        setUpInfo();
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(directoryWithCourses + "/info.json"), "UTF-8"
                )
        )) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(info, writer);
        }

    }

    private void zipCourses() throws FileNotFoundException {
        for (File f : directoryWithCourses.listFiles()) {
            if (f.isDirectory() && directoryContainsCourseJson(f)) {
                String pathToCourse = f.getAbsolutePath();
                Zipper.zip(pathToCourse);
            }
        }
    }

    private void setUpInfo() throws IOException {
        for (File f : directoryWithCourses.listFiles()) {
            if (f.isDirectory() && directoryContainsCourseJson(f)) {
                String pathToCourse = f.getAbsolutePath();

                Course course = JsonParser.parseJson(pathToCourse + "/course.json", Course.class);

                String pathToZip = pathToCourse + ".zip";
                File zip = new File(pathToZip);

                course.setLogoUrl(LOGO_URL_BASE + f.getName() + "/" + course.getLogo());
                course.setSize((int) zip.length());
                course.setVersion(tag);
                course.setSha2Hash(Hasher.hash(pathToZip));
                course.setUrl(COURSE_URL_BASE + tag + "/" + zip.getName());
                course.setLogo(null);
                info.getCourses().add(course);
            }
        }
        String update = now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.uuuu"));
        info.setUpdate(update);
    }

    private boolean directoryContainsCourseJson(File directory) {
        for (File f : directory.listFiles()) {
            if (f.getName().equals("course.json")) {
                return true;
            }
        }
        return false;
    }
}
