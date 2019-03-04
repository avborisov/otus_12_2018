package ru.otus.borisov.atm.money.ruble;

import ru.otus.borisov.atm.money.notes.BankNoteHolder;
import ru.otus.borisov.atm.money.notes.BankNotesAcceptor;
import ru.otus.borisov.atm.money.notes.BankNoteType;

import java.util.*;

public class RubleBankNotesAcceptor<T extends RubleBankNote> implements BankNotesAcceptor<T> {

    SortedMap<RubleBankNoteType, BankNoteHolder> holders;

    public RubleBankNotesAcceptor(Map<RubleBankNoteType, BankNoteHolder> availableHolders){
        holders = new TreeMap<>(new BankNoteTypeComparator());
        holders.putAll(availableHolders);
    }

    public boolean isWrongTypeOfMoney(T note) {
        BankNoteType noteType = note.getType();
        if (holders.containsKey(noteType)) {
            return false;
        }
        return true;
    }

    @Override
    public int getBalance() {
        int totalBalance = 0;
        for (BankNoteType type : holders.keySet()) {
            totalBalance += holders.get(type).getMoneyCount() * type.getValue();
        }
        return totalBalance;
    }

    @Override
    public boolean putMoney(Collection<T> notes) {
        if (!canPut(notes)) {
            return false;
        }
        for (T note : notes) {
            BankNoteHolder holder = holders.get(note.getType());
            holder.put();
        }
        return true;
    }

    @Override
    public Collection<T> pullMoney(int sum) {
        List<T> notes = notesForPull(sum);
        for (RubleBankNote note : notes) {
            holders.get(note.getType()).pull();
        }
        return notes;
    }

    private boolean canPut(Collection<T> notes) {
        Map<BankNoteType, Integer> notesByType = new HashMap<>();
        for (T note : notes) {
            BankNoteType type = note.getType();
            if (isWrongTypeOfMoney(note)) {
                System.err.println(ERROR_CANT_PUT_THIS_TYPE_OF_BANKNOTES + type.getName());
                return false;
            }
            Integer notesOfTypeCount = notesByType.get(type);
            notesByType.put(type, notesOfTypeCount == null ? 0 : notesOfTypeCount + 1);
        }
        for (BankNoteType type : notesByType.keySet()) {
            BankNoteHolder holder = holders.get(type);
            Integer freePlacesInHolder = holder.getMaxCount() - holder.getMoneyCount();
            if (freePlacesInHolder < notesByType.get(type)) {
                System.err.println(ERROR_NOT_ENOUGH_SPACE_FOR_BANKNOTES + type.getName());
                return false;
            }
        }
        return true;
    }

    private List<T> notesForPull(int sum) {
        int countSum = sum;
        List<T> notes = new ArrayList<>();
        for (RubleBankNoteType type : holders.keySet()) {
            BankNoteHolder holder = holders.get(type);
            int availableMoneyCount = holder.getMoneyCount();
            while ((countSum - type.getValue()) >= 0 && availableMoneyCount > 0) {
                countSum -= type.getValue();
                availableMoneyCount--;
                notes.add((T) new RubleBankNote(type));
            }
        }
        if (countSum == 0) {
            return notes;
        }
        System.err.println(ERROR_CANT_GET_SUM + sum);
        return Collections.emptyList();
    }

    private class BankNoteTypeComparator implements Comparator<BankNoteType>{
        @Override
        public int compare(BankNoteType e1, BankNoteType e2) {
            return e2.getValue() - (e1.getValue());
        }
    }


}
