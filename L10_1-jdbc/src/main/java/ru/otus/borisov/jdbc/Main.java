package ru.otus.borisov.jdbc;

import org.postgresql.Driver;
import ru.otus.borisov.jdbc.dao.Executor;
import ru.otus.borisov.jdbc.dao.UserResultHandler;
import ru.otus.borisov.jdbc.model.UserDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost/postgres";

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","postgres");

        Connection connection = DriverManager.getConnection(DATABASE_URL, props);

        Executor<UserDataSet> executor = new Executor<UserDataSet>(connection);
        UserResultHandler handler = new UserResultHandler();

        UserDataSet user_1 = new UserDataSet();
        user_1.setName("Alex");
        user_1.setAge(32);
        executor.save(user_1);

        UserDataSet user_2 = new UserDataSet();
        user_2.setName("Piter");
        user_2.setAge(20);
        executor.save(user_2);

        UserDataSet selectedUser = executor.load(1, UserDataSet.class, handler);
        if (selectedUser != null) {
            System.out.println("Selected user: \n" + selectedUser.getName() + "\n" + selectedUser.getAge());
        }

        connection.close();
    }

}
