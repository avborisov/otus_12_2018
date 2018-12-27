package ru.otus.borisov;

import org.apache.commons.lang3.StringUtils;

public class Main {

    public static void main(String[] args) {
        Main mn = new Main();
        System.out.println(mn.anotherObfuscatedMethod());
    }

    public String thisMethodWouldBeObfuskated(String param) {
        return StringUtils.capitalize(param);
    }

    public String anotherObfuscatedMethod() {
        final String someString = "string in obfuscated method";
        return thisMethodWouldBeObfuskated(someString);
    }

}
