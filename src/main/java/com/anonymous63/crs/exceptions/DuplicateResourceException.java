package com.anonymous63.crs.exceptions;

public class DuplicateResourceException extends RuntimeException {
    final String entityName;
    final transient Object fieldValue;

    public DuplicateResourceException(String entityName, Object fieldValue) {
        super(String.format("%s already exists with : '%s'", entityName, fieldValue));
        this.entityName = entityName;
        this.fieldValue = fieldValue;
    }
}
