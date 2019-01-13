package ru.otus.borisov.calc;

import ru.otus.borisov.factory.IObjectFactory;

import java.lang.instrument.Instrumentation;

@Deprecated
/**
 * We can't believe Runtime get memory results
 */
public class RuntimeMemorySizeCalculator implements IObjectSizeCalculator {

    public long getObjectSize(IObjectFactory factory) {
        try {
            long memoryBeforeFactory = getMem();

            final int arraySize = 20_000_000;

            Object[] array = new Object[arraySize];

            for (int i = 0; i < arraySize; i++) {
                array[i] = factory.getObject();
            }

            long memoryAfterFactory = getMem();
            long singleObjectSize = (memoryAfterFactory - memoryBeforeFactory) / arraySize;

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
