package ru.otus.borisov.atm.money.notes;

import ru.otus.borisov.atm.money.Money;

public interface BankNote extends Money {
    public BankNoteType getType();
}
