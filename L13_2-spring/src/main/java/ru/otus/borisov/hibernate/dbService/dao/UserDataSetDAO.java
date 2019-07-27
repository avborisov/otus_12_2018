package ru.otus.borisov.hibernate.dbService.dao;

import org.hibernate.Session;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

}
