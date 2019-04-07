package ru.otus.borisov.hibernate.dbService.dao;

import org.hibernate.Session;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;

public class UserDataSetDAO {

    Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet user) {
        session.save(user);
    }

    public UserDataSet read(long id) {
        return session.get(UserDataSet.class, id);
    }

}
