package ru.otus.borisov.calc;

import ru.otus.borisov.factory.IObjectFactory;

import java.lang.instrument.Instrumentation;

/**
 * We can't believe Runtime get memory results
 */
public class RuntimeMemorySizeCalculator implements IObjectSizeCalculator {

    public long getObjectSize(IObjectFactory factory) {
        try {
            final int arraySize = 20_000_000;

            long memoryBeforeFactory = getMem();

            Object[] array = new Object[arraySize];

            long memoryWithReference = getMem();
            System.out.println("Reference size: " + (memoryWithReference - memoryBeforeFactory) / arraySize);

            for (int i = 0; i < array.length; i++) {
                array[i] = factory.getObject();
            }

            long memoryAfterFactory = getMem();
            long singleObjectSize = (memoryAfterFactory - memoryWithReference) / arraySize;

            System.out.println("Object size: " + singleObjectSize);

            array = null;

            System.gc();
            Thread.sleep(1000);

            return singleObjectSize;

        } catch (InterruptedException e) {
            System.out.println("Can't get memory value from runtime;");
        }
        return 0;
    }

    private long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}
