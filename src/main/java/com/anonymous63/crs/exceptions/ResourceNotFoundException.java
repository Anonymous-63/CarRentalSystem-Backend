package com.anonymous63.crs.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    final String entityName;
    final transient Object fieldValue;

    public ResourceNotFoundException(String entityName, Object fieldValue) {
        super(String.format("%s not found with : '%s'", entityName, fieldValue));
        this.entityName = entityName;
        this.fieldValue = fieldValue;
    }
}
