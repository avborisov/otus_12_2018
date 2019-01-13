package ru.otus.borisov.factory;

public interface IObjectFactory<T> {

    public T getObject();

    String getObjectType();
}
