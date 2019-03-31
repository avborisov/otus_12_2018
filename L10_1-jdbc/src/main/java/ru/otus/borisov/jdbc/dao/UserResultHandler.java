package ru.otus.borisov.jdbc.dao;

import ru.otus.borisov.jdbc.model.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultHandler extends TResultHandler<UserDataSet> {

    public UserDataSet handle(ResultSet resultSet) {
        try {
            resultSet.next();
            UserDataSet user = new UserDataSet();
            user.setName(resultSet.getString(UserDataSet.NAME_FIELD_NAME));
            user.setAge(resultSet.getInt(UserDataSet.AGE_FIELD_NAME));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
