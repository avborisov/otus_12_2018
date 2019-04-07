package ru.otus.borisov.hibernate.dbService.jdbc;

import ru.otus.borisov.hibernate.base.dataSets.DataSet;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class Executor<T extends DataSet> {

    private Connection connection;
    TResultHandler<T> handler;

    public Executor(Connection connection, TResultHandler<T> handler) {
        this.handler = handler;
        this.connection = connection;
    }

    public boolean save(T dataSet) throws IllegalAccessException, SQLException {
        String query = generateInsert(dataSet);
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        stmt.close();
        return true;
    }

    public T load(long id, Class<T> clazz) throws SQLException {
        String query = generateSelect(clazz, id);
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    private String generateInsert(T dataSet) throws IllegalAccessException {

        Table tableNameAnnotation = dataSet.getClass().getAnnotation(Table.class);
        if (tableNameAnnotation == null) {
            throw new MissDBTableAnnotationException("You must use @DBTable annotation with table name provided");
        }

        StringBuilder query = new StringBuilder("INSERT INTO ").append("\"").append(tableNameAnnotation.name()).append("\"");

        StringBuilder names = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" (");

        Map<String, String> namesWithValues = getNamesWithValues(dataSet);
        for (String key : namesWithValues.keySet()) {
            names.append(key).append(",");

            Object value = namesWithValues.get(key);
            values.append(value).append(",");
        }
        names.deleteCharAt(names.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");

        query.append(names).append(" VALUES ").append(values);

        return query.toString();
    }

    private String generateSelect(Class<T> type, long id) {

        StringBuilder query = new StringBuilder("SELECT id,");

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Table nameAnnotation = field.getAnnotation(Table.class);
            if (nameAnnotation != null) {
                query.append(nameAnnotation.name()).append(",");
            }
        }
        Table tableNameAnnotation = type.getAnnotation(Table.class);
        if (tableNameAnnotation == null) {
            throw new MissDBTableAnnotationException("You must use @DBTable annotation with table name provided");
        }
        query.deleteCharAt(query.length() - 1).append(" FROM ").append("\"" + tableNameAnnotation.name() + "\"");
        query.append(" WHERE id = ").append(id);

        return query.toString();
    }

    private Map<String, String> getNamesWithValues(T dataSet) throws IllegalAccessException {
        Map<String, String> values = new HashMap<String, String>();
        Field[] fields = dataSet.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column nameAnnotation = field.getAnnotation(Column.class);
            if (nameAnnotation != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                Object value = field.get(dataSet);

                if (fieldType.isPrimitive()) {
                    if (isFieldTypeIsNumeric(fieldType)) {
                        values.put(nameAnnotation.name(), value.toString());
                    } else if (fieldType.equals(Boolean.TYPE)) {
                        values.put(nameAnnotation.name(), value.toString());
                    } else{
                        values.put(nameAnnotation.name(), "'" + value.toString() + "'");
                    }
                } else if (fieldType.equals(String.class)) {
                    values.put(nameAnnotation.name(), "'" + value.toString() + "'");
                } else {
                    throw new UnsupportedFieldTypeException("Unsupported type of field in DataSet");
                }
            }
        }
        return values;
    }

    private boolean isFieldTypeIsNumeric(Class fieldType) {
        return fieldType.equals(Byte.TYPE) || fieldType.equals(Integer.TYPE) || fieldType.equals(Short.TYPE)
                || fieldType.equals(Float.TYPE) || fieldType.equals(Float.TYPE);
    }


}
