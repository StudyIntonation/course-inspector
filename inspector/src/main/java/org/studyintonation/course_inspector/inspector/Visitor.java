package org.studyintonation.course_inspector.inspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface Visitor {
    void visit(Visitable visitable);

    default void findSatisfyingFieldsAndDoAction(Object object, BiPredicate<Object, Field> condition,
                                                 BiConsumer<Object, Field> action) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (condition.test(object, field)) {
                action.accept(object, field);
            } else {
                Object innerFieldInstance = null;
                try {
                    field.setAccessible(true);
                    innerFieldInstance = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (objectIsListOfData(innerFieldInstance)) {

                    for (Object x : (ArrayList) innerFieldInstance) {
                        findSatisfyingFieldsAndDoAction(x, condition, action);
                    }
                }
            }
        }
    }

    default boolean fieldNameIsOneOf(String fieldName, String... fileFieldsNames) {
        return Arrays.stream(fileFieldsNames)
                .anyMatch(fieldName::equals);
    }

    static boolean objectIsListOfData(Object object) {
        return object != null && object.getClass().isAssignableFrom(ArrayList.class);
    }

}
