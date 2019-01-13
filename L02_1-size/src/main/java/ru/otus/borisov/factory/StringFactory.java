package ru.otus.borisov.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFactory implements IObjectFactory<String> {

    private boolean isEmpty;

    public StringFactory(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public String getObject() {
        if (isEmpty) {
            return new String();
        }
        return new String("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
                "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }

    @Override
    public String getObjectType() {
        return String.class.getTypeName();
    }
}
