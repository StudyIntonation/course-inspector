package org.studyintonation.course_inspector.inspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

//TODO give more detailed information about unavailable fields
public class FieldsAvailabilityInspectionVisitor implements Visitor {
    private List<String> infoAboutUnavailableFields = new ArrayList<>();

    @Override
    public void visit(Visitable visitable) {
        collectInfoAboutUnavailableFields(visitable);
        if (infoAboutUnavailableFields.size() != 0) {
            StringBuilder sb = new StringBuilder();
            infoAboutUnavailableFields.forEach(name -> sb.append(name).append("\n"));
            throw new FieldsUnavailableException(sb.toString());
        }
    }

    private void collectInfoAboutUnavailableFields(Object object) {
        findSatisfyingFieldsAndDoAction(
                object,
                this::fieldIsUnavailable,
                (fieldOwner, field) -> infoAboutUnavailableFields.add(
                        fieldOwner.getClass().getSimpleName() + "." + field.getName()
                )
        );
    }

    private boolean fieldIsUnavailable(Object fieldOwner, Field field) {
        Object fieldValue = null;
        try {
            field.setAccessible(true);
            fieldValue = field.get(fieldOwner);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return isNull(fieldValue) || fieldValue.equals("") || listIsEmpty(fieldValue);
    }

    @SuppressWarnings("rawtypes")
    private boolean listIsEmpty(Object mayBeList) {
        if (mayBeList.getClass().isAssignableFrom(ArrayList.class)) {
            return ((ArrayList) mayBeList).isEmpty();
        }
        return false;
    }
}
