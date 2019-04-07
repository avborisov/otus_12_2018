package ru.otus.borisov.hibernate.dbService.jdbc;

import java.sql.ResultSet;

public abstract class TResultHandler<T> {

    public abstract T handle(ResultSet resultSet);
}
