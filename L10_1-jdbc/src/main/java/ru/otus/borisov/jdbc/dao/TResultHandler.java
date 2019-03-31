package ru.otus.borisov.jdbc.dao;

import java.sql.ResultSet;

public abstract class TResultHandler<T> {

    public abstract T handle(ResultSet resultSet);
}
