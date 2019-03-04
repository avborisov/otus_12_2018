package ru.otus.borisov.atm.money.ruble;

import ru.otus.borisov.atm.money.notes.BankNote;

import java.util.Objects;

public class RubleBankNote implements BankNote {

    private RubleBankNoteType type;

    public RubleBankNote(RubleBankNoteType type) {
        this.type = type;
    }

    public int getValue() {
        return type.getValue();
    }

    public String getName() {
        return type.getName();
    }

    public RubleBankNoteType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubleBankNote that = (RubleBankNote) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
