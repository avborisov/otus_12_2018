package ru.otus.borisov.hibernate.dbService.hbrn;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Service;
import ru.otus.borisov.hibernate.dbService.DBService;
import ru.otus.borisov.hibernate.base.dataSets.AddressDataSet;
import ru.otus.borisov.hibernate.base.dataSets.PhoneDataSet;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;
import ru.otus.borisov.hibernate.dbService.dao.UserDataSetDAO;

@Service
public class UserHibernateDbService implements DBService<UserDataSet> {

    private final SessionFactory sessionFactory;

    public UserHibernateDbService() {
        Configuration configuration = getConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/postgres");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "postgres");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void create(UserDataSet data) {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.getTransaction();
            transaction.begin();
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(data);
            transaction.commit();
        }
    }

    public UserDataSet read(long id) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        }
    }

    public Integer count() {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll().size();
        }
    }
}
