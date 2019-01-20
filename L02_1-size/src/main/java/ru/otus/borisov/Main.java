package ru.otus.borisov;

import ru.otus.borisov.calc.InstrumentationSizeCalculator;
import ru.otus.borisov.calc.RuntimeMemorySizeCalculator;
import ru.otus.borisov.factory.ArrayListFactory;
import ru.otus.borisov.factory.PrimitiveArrayFactory;
import ru.otus.borisov.factory.StringFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        RuntimeMemorySizeCalculator calculator = new RuntimeMemorySizeCalculator();
        //InstrumentationSizeCalculator calculator = new InstrumentationSizeCalculator();

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("empty String");
        System.out.println("Size of empty String: " + calculator.getObjectSize(new StringFactory(true)));

        System.gc();
        Thread.sleep(100);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("Lorem ipsum String");
        System.out.println("Size of Lorem ipsum String: " + calculator.getObjectSize(new StringFactory(false)));

        System.gc();
        Thread.sleep(100);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("empty ArrayList");
        System.out.println("Size of empty ArrayList: " + calculator.getObjectSize(new ArrayListFactory<Integer>(Integer.class)));

        System.gc();
        Thread.sleep(100);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("ArrayList with Integer object");
        System.out.println("Size of ArrayList with Integer objects: " + calculator.getObjectSize(new ArrayListFactory<Integer>(Integer.class, 1, 2, 3)));

        System.gc();
        Thread.sleep(100);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("Empty int array");
        System.out.println("Size of empty int array: " + calculator.getObjectSize(new PrimitiveArrayFactory(true)));

        System.gc();
        Thread.sleep(100);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println("int array");
        System.out.println("Size of int array: " + calculator.getObjectSize(new PrimitiveArrayFactory(false)));
    }

}
