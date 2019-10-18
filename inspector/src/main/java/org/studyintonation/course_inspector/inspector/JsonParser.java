package org.studyintonation.course_inspector.inspector;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class JsonParser {
    public static <T> T parseJson(String pathToFile, Class<T> clazz) throws FileNotFoundException,
            MalformedJsonException, JsonParseException {
        //TODO     findbugs:
        //TODO     Found a call to a method which will perform a byte to String
        //TODO     (or String to byte) conversion, and will assume that the default
        //TODO     platform encoding is suitable. This will cause the application
        //TODO     behaviour to vary between platforms. Use an alternative API and
        //TODO     specify a charset name or Charset object explicitly.
        try (JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(pathToFile), StandardCharsets.UTF_8))) {
            Gson gson = new Gson();
            T parsedObject = gson.fromJson(jsonReader, clazz);
            if (parsedObject == null) {
                throw new JsonParseException("File " + pathToFile + " is empty");
            }
            return parsedObject;
        } catch (IOException e) {
            if (e instanceof MalformedJsonException) {
                throw new MalformedJsonException(e.getMessage().replaceFirst("\\D+Exception: ", ""));
            }
            if (e instanceof FileNotFoundException) {
                throw (FileNotFoundException) e;
            }
            e.printStackTrace();
        }
        return null;
    }
}
