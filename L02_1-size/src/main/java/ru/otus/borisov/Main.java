package ru.otus.borisov;

import ru.otus.borisov.calc.InstrumentationSizeCalculator;
import ru.otus.borisov.calc.RuntimeMemorySizeCalculator;
import ru.otus.borisov.factory.ArrayListFactory;
import ru.otus.borisov.factory.StringFactory;

public class Main {

    public static void main(String[] args) {

        //RuntimeMemorySizeCalculator calculator = new RuntimeMemorySizeCalculator();
        InstrumentationSizeCalculator calculator = new InstrumentationSizeCalculator();

        StringFactory emptyStringFactory = new StringFactory(true);
        long emptyStringSize = calculator.getObjectSize(emptyStringFactory);
        System.out.println("Size of empty String: " + emptyStringSize);

        StringFactory stringFactory = new StringFactory(true);
        long stringSize = calculator.getObjectSize(stringFactory);
        System.out.println("Size of String: " + stringSize);

        ArrayListFactory<Integer> emptyArrayListFactory = new ArrayListFactory<Integer>(Integer.class);
        long emptyArrayListSize = calculator.getObjectSize(emptyArrayListFactory);
        System.out.println("Size of empty ArrayList: " + emptyArrayListSize);

        ArrayListFactory<Integer> IntegerArrayListFactory = new ArrayListFactory<Integer>(Integer.class, 1, 2, 3);
        long arrayListSize = calculator.getObjectSize(IntegerArrayListFactory);
        System.out.println("Size of ArrayList with Integer objects: " + arrayListSize);
    }

}
