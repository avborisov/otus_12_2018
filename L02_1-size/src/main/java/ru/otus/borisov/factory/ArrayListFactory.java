package ru.otus.borisov.factory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListFactory<T> implements IObjectFactory<ArrayList<T>> {

    private final Class<T> clazz;
    private final T[] objects;

    public ArrayListFactory(Class<T> clazz, T... objects) {
        this.objects = objects;
        this.clazz = clazz;
    }

    @Override
    public ArrayList<T> getObject() {
        if (objects.length == 0) {
            return new ArrayList<T>();
        }
        ArrayList<T> result = new ArrayList<>(Arrays.asList(objects));

        return result;
    }

    @Override
    public String getObjectType() {
        return "ArrayList<" + clazz + ">";
    }

}
