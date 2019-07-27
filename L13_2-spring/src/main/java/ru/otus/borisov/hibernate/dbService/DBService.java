package ru.otus.borisov.hibernate.dbService;

import ru.otus.borisov.hibernate.base.dataSets.DataSet;

public interface DBService<T extends DataSet> {

    public void create(T data);
    public T read(long id);
    public Integer count();

}
