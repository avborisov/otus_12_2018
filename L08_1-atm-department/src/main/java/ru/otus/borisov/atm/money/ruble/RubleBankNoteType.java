package ru.otus.borisov.atm.money.ruble;

import ru.otus.borisov.atm.money.notes.BankNoteType;

public enum RubleBankNoteType implements BankNoteType {

    ONE_HUNDRED("One hundred rubles", 100),
    FIVE_HUNDRED("Five hundred rubles", 500),
    ONE_THOUSAND("One thousand rubles", 1000),
    FIVE_THOUSAND("Five thousand rubles", 5000);

    private String name;
    private int value;

    private RubleBankNoteType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
