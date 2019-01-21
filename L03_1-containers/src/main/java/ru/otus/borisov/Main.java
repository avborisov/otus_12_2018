package ru.otus.borisov;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> myList = new MyArrayList<>();

        myList.add("1");
        myList.add("3");
        myList.add("2");

        Collections.addAll(myList, "5", "4");
        System.out.println(myList);

        List<String> myListCopy = new MyArrayList<>();
        myListCopy.add("10");
        myListCopy.add("11");
        myListCopy.add("12");
        myListCopy.add("13");
        myListCopy.add("14");
        myListCopy.add("15");

        Collections.copy(myListCopy, myList);
        System.out.println(myListCopy);

        Collections.sort(myList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(myList);

        myList.addAll(1, myListCopy);
        System.out.println(myList);
    }

}
