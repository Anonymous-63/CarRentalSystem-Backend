package com.anonymous63.crs.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Global {
    // Get all field names from the object
    public String[] getFieldNames(Object obj){
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }

        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName(); // Store the field names
        }

        return fieldNames;
    }

    // Get a specific field name from the object
    public String getFieldName(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name must not be null or empty");
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            return field.getName(); // Return the field name
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field " + fieldName + " does not exist in " + obj.getClass().getSimpleName(), e);
        }
    }
}
