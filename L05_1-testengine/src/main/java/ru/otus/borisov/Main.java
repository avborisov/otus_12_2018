package ru.otus.borisov;

import ru.otus.borisov.jtest.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        try {
            TestRunner.runTest(ApplicationTest.class);
        } catch (Throwable e) {
            System.out.println("Something wrong, " + e.getLocalizedMessage());
        }
    }

}
