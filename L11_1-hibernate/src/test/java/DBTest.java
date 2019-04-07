import org.junit.Test;
import ru.otus.borisov.hibernate.base.dataSets.AddressDataSet;
import ru.otus.borisov.hibernate.base.dataSets.PhoneDataSet;
import ru.otus.borisov.hibernate.base.dataSets.UserDataSet;
import ru.otus.borisov.hibernate.dbService.hbrn.UserHibernateDbService;
import ru.otus.borisov.hibernate.dbService.jdbc.Executor;
import ru.otus.borisov.hibernate.dbService.jdbc.UserDBService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;

public class DBTest {

    @Test
    public void testHibernate() {
        UserHibernateDbService db = new UserHibernateDbService();

        AddressDataSet address = new AddressDataSet();
        address.setStreet("Lenina 1");

        UserDataSet user = new UserDataSet();

        PhoneDataSet phoneMobile = new PhoneDataSet();
        phoneMobile.setNumber("9229001010");
        phoneMobile.setUser(user);

        PhoneDataSet phoneCity = new PhoneDataSet();
        phoneCity.setNumber("343100000");
        phoneCity.setUser(user);

        HashSet<PhoneDataSet> phones = new HashSet<>();
        phones.add(phoneMobile);
        phones.add(phoneCity);

        user.setName("Alex");
        user.setAge(32);
        user.setPhones(phones);
        user.setAddress(address);

        db.create(user);
        UserDataSet userFromDb = db.read(1);

        assert(userFromDb.equals(user));
    }

}
