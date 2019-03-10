package ru.otus.borisov.atm.money.notes;

import ru.otus.borisov.atm.money.MoneyAcceptor;

public interface BankNotesAcceptor<T extends BankNote> extends MoneyAcceptor<T> {

    public static final String ERROR_CANT_GET_SUM = "Can't get this sum: ";
    public static final String ERROR_NOT_ENOUGH_SPACE_FOR_BANKNOTES = "Not enough space for banknotes of type: ";
    public static final String ERROR_CANT_PUT_THIS_TYPE_OF_BANKNOTES = "Can't put this type of banknotes: ";

}
