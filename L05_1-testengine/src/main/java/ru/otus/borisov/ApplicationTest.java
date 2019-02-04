package ru.otus.borisov;

import ru.otus.borisov.jtest.annotations.*;

public class ApplicationTest {

    private Integer counter;

    @BeforeAll
    public static void initSomething() {
        System.out.println("Something initiated!");
    }

    @AfterAll
    public static void destroySomething() {
        System.out.println("Something destroyed!");
    }

    @Test
    public void testFirst() {
        System.out.println("Test number one!");
        counter++;
    }

    @Test
    public void testSecond() throws Exception {
        System.out.println("Test number two!");
        counter+=2;
        throw new Exception("fail");
    }

    @Test
    public void testThird() {
        System.out.println("Test number three!");
        counter*=3;
    }

    @BeforeEach
    public void doSomethingBefore() {
        counter = 10;
        System.out.println("Counter before test: " + counter);
    }

    @AfterEach
    public void doSomethingAfter() {
        System.out.println("Counter after test: " + counter);
        counter = null;
    }

}
