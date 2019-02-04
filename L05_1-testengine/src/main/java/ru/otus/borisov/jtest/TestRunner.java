package ru.otus.borisov.jtest;

import ru.otus.borisov.jtest.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

        if (!isMethodsStatic(beforeAllMethods) | !isMethodsStatic(afterAllMethods)) {
            return;
        }

        // Run before all methods:
        invokeStaticMethodsOfClass(beforeAllMethods);

        // Run test methods with beforeEach/afterEach:
        for (final Method method : testMethods) {
            try {
                Object testObject = clazz.getConstructor().newInstance();
                invokeMethodsOfClass(testObject, beforeEachMethods);
                invokeMethodOfClass(testObject, method);
                invokeMethodsOfClass(testObject, afterEachMethods);
                System.out.println("Test passed!\n\n");
            } catch (Throwable e) {
                System.out.println("Test failed, method name: " + method.getName());
                printStackTrace(e);
            }
        }

        // Run after all methods:
        invokeStaticMethodsOfClass(afterAllMethods);
    }

    private static void printStackTrace(Throwable e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter= new PrintWriter(writer);
        e.printStackTrace(printWriter);
        System.out.println("Exception is " + writer.toString() + "\n\n");
    }

    private static boolean isMethodsStatic(List<Method> methods) {
        List<Method> nonStatic = new ArrayList<Method>();
        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                nonStatic.add(method);
                System.out.println("Test failed, @BeforeAll and @AfterAll methods must be declared as static, method name: " + method.getName());
            }
        }
        return nonStatic.size() == 0;
    }

    private static void invokeMethodsOfClass(Object o, List<Method> beforeAllMethods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : beforeAllMethods) {
            invokeMethodOfClass(o, method);
        }
    }

    private static void invokeStaticMethodsOfClass(List<Method> beforeAllMethods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : beforeAllMethods) {
            invokeStaticMethodOfClass(method);
        }
    }

    private static void invokeMethodOfClass(Object o, Method method) throws IllegalAccessException, InvocationTargetException {
        method.invoke(o);
    }

    private static void invokeStaticMethodOfClass(Method method) throws IllegalAccessException, InvocationTargetException {
        method.invoke(null);
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
