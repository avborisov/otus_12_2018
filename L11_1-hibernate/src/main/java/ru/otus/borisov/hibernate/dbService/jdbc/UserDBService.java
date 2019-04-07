package ru.otus.borisov.hibernate.dbService.jdbc;

import org.postgresql.Driver;
import ru.otus.borisov.hibernate.base.DBService;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UserDBService implements DBService<UserDataSet> {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost/postgres";
    private UserResultHandler handler;

    public UserDBService() {
        handler = new UserResultHandler();
    }

    public void create(UserDataSet data) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, getProperties())) {
            Executor<UserDataSet> executor = new Executor<>(connection, handler);
            executor.save(data);
        } catch (Throwable e) {
            // log and alert to user
            e.printStackTrace();
        }
    }

    public UserDataSet read(long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, getProperties())) {
            Executor<UserDataSet> executor = new Executor<>(connection, handler);
            return executor.load(id, UserDataSet.class);
        } catch (Throwable e) {
            // log and alert to user
            e.printStackTrace();
        }
        return null;
    }

    private Properties getProperties() throws SQLException {
        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        return props;
    }
}
