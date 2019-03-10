package ru.otus.borisov.atm.money.notes;

import ru.otus.borisov.atm.money.MoneyHolder;

public class BankNoteHolder implements MoneyHolder, Cloneable {

    private int count;
    private int maxCount;

    public BankNoteHolder(int maxCount) {
        this.count = 0;
        this.maxCount = maxCount;
    }

    public int getMoneyCount() {
        return count;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean put() {
        if (count < maxCount) {
            count++;
            return true;
        }
        System.err.println("Can't put in holder, max notes reached");
        return false;
    }

    public boolean pull() {
        if (count > 0) {
            count--;
            return true;
        }
        System.err.println("Can't pull from holder, have't notes in holder");
        return false;
    }
}
