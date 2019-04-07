package ru.otus.borisov.hibernate.dbService.jdbc;

import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultHandler extends TResultHandler<UserDataSet> {

    public static final String NAME_FIELD_NAME = "user_name";
    public static final String AGE_FIELD_NAME = "user_age";

    public UserDataSet handle(ResultSet resultSet) {
        try {
            resultSet.next();
            UserDataSet user = new UserDataSet();
            user.setName(resultSet.getString(NAME_FIELD_NAME));
            user.setAge(resultSet.getInt(AGE_FIELD_NAME));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
