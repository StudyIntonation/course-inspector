package org.studyintonation.course_inspector.info_generator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class Zipper {
    private static int prefixLength;

    static void zip(String pathToDirectory) throws FileNotFoundException {
        File directory = new File(pathToDirectory);

        if (!directory.exists()) {
            throw new FileNotFoundException("Can not zip directory " + pathToDirectory + ": it does not exist");
        }

        prefixLength = directory.getAbsolutePath().lastIndexOf(File.separator) + 1;

        try (ZipOutputStream zipArchive = new ZipOutputStream(
                new FileOutputStream(directory.getAbsolutePath() + ".zip"))) {
            zip(directory, zipArchive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zip(File directory, ZipOutputStream zipArchive) {
        for (File f : directory.listFiles()) {
            if (f.isDirectory())
                zip(f, zipArchive);
            else {
                //TODO use new api Files.readAllBytes
                try (InputStream in = new FileInputStream(f)) {
                    zipArchive.putNextEntry(new ZipEntry(f.getPath().substring(prefixLength)));
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) >= 0) {
                        zipArchive.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
