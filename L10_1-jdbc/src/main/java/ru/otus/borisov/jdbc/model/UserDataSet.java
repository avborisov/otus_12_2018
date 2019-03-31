package ru.otus.borisov.jdbc.model;

/**
 * POSTGRES:
 * CREATE TABLE "user"
 * (
 *   id BIGSERIAL PRIMARY KEY,
 *   user_name VARCHAR(255),
 *   user_age INTEGER
 * );
 */
@DBTable(name = "user")
public class UserDataSet extends DataSet {

    public static final String NAME_FIELD_NAME = "user_name";
    public static final String AGE_FIELD_NAME = "user_age";

    @DBField(name = NAME_FIELD_NAME)
    private String name;
    @DBField(name = AGE_FIELD_NAME)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
