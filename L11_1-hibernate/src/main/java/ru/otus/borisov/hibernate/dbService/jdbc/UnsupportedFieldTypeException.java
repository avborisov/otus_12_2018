package ru.otus.borisov.hibernate.dbService.jdbc;

public class UnsupportedFieldTypeException extends RuntimeException {

    public UnsupportedFieldTypeException(String message) {
        super(message);
    }

}
