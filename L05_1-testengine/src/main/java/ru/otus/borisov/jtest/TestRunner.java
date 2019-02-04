package ru.otus.borisov.jtest;

import ru.otus.borisov.jtest.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunner {

    public static void runTest(Class clazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        List<Method> beforeAllMethods = getMethodsAnnotatedWith(clazz, BeforeAll.class);
        List<Method> afterAllMethods = getMethodsAnnotatedWith(clazz, AfterAll.class);
        List<Method> beforeEachMethods = getMethodsAnnotatedWith(clazz, BeforeEach.class);
        List<Method> afterEachMethods = getMethodsAnnotatedWith(clazz, AfterEach.class);
        List<Method> testMethods = getMethodsAnnotatedWith(clazz, Test.class);

        // Run before all methods:
        Object beforeAllObject = clazz.getConstructor().newInstance();
        invokeMethodsOfClass(clazz, beforeAllObject, beforeAllMethods);

        // Run test methods with beforeEach/afterEach:
        for (final Method method : testMethods) {
            Object testObject = clazz.getConstructor().newInstance();
            invokeMethodsOfClass(clazz, testObject, beforeEachMethods);
            invokeMethodOfClass(clazz, testObject, method);
            invokeMethodsOfClass(clazz, testObject, afterEachMethods);
        }

        // Run after all methods:
        Object afterAllObject = clazz.getConstructor().newInstance();
        invokeMethodsOfClass(clazz, afterAllObject, afterAllMethods);
    }

    private static void invokeMethodsOfClass(Class clazz, Object o, List<Method> beforeAllMethods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : beforeAllMethods) {
            invokeMethodOfClass(clazz, o, method);
        }
    }

    private static void invokeMethodOfClass(Class clazz, Object o, Method method) throws IllegalAccessException, InvocationTargetException {
        method.invoke(o);
    }

    private static List<Method> getMethodsAnnotatedWith(final Class clazz, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(clazz.getDeclaredMethods()));
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
        return methods;
    }

}
