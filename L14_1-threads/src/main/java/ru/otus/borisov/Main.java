package ru.otus.borisov;

public class Main {

    public static void main(String[] args) {
        new LockThreadTest();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("concurrence error");
            return;
        }
        System.out.println("--------------------------------");

        new SyncThreadTest();
    }

}
