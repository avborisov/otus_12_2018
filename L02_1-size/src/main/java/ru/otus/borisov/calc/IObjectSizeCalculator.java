package ru.otus.borisov.calc;

import ru.otus.borisov.factory.IObjectFactory;

public interface IObjectSizeCalculator {

    public long getObjectSize(IObjectFactory factory);

}
