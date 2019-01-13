package ru.otus.borisov.calc;

import ru.otus.borisov.factory.IObjectFactory;

import java.lang.instrument.Instrumentation;

/**
 * Add the following to your MANIFEST.MF:
 *
 * Premain-Class: ru.otus.borisov.calc.InstrumentationSizeCalculator
 *
 * Pack this class and MANIFEST.MF to InstrumentationSizeCalculator.jar
 *
 * Invoke with:
 *
 * java -javaagent:InstrumentationSizeCalculator.jar mainClass
 */
public class InstrumentationSizeCalculator implements IObjectSizeCalculator {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public long getObjectSize(IObjectFactory factory) {
        return instrumentation.getObjectSize(factory.getObject());
    }

}
