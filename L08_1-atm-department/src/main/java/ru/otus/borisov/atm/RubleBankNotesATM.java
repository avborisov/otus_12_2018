package ru.otus.borisov.atm;

import ru.otus.borisov.atm.money.notes.BankNote;
import ru.otus.borisov.atm.money.notes.BankNoteHolder;
import ru.otus.borisov.atm.money.notes.BankNoteType;
import ru.otus.borisov.atm.money.ruble.RubleBankNoteType;
import ru.otus.borisov.atm.money.ruble.RubleBankNotesAcceptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RubleBankNotesATM<T extends BankNote> implements ATM<T> {

    RubleBankNotesAcceptor acceptor;

    public RubleBankNotesATM() {
        acceptor = new RubleBankNotesAcceptor(getHolders());
    }

    public int getBalance() {
        return acceptor.getBalance();
    }

    public Collection<T> getMoney(int sum) {
        return acceptor.pullMoney(sum);
    }

    public void putMoney(Collection<T> notes) {
        acceptor.putMoney(notes);
    }

    public void saveDefaultState() {
        acceptor.saveDefaultState();
    }

    public boolean returnToDefaultState() {
        return acceptor.returnToDefaultState();
    }

    private Map<BankNoteType, BankNoteHolder> getHolders() {
        int maxNotesInHolder = 100;

        Map<BankNoteType, BankNoteHolder> holders = new HashMap<>();

        holders.put(RubleBankNoteType.ONE_HUNDRED, new BankNoteHolder(maxNotesInHolder));
        holders.put(RubleBankNoteType.FIVE_HUNDRED, new BankNoteHolder(maxNotesInHolder));
        holders.put(RubleBankNoteType.ONE_THOUSAND, new BankNoteHolder(maxNotesInHolder));
        holders.put(RubleBankNoteType.FIVE_THOUSAND, new BankNoteHolder(maxNotesInHolder));

        return holders;
    }
}
