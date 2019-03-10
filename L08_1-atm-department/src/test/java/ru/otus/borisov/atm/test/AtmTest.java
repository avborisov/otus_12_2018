package ru.otus.borisov.atm.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.borisov.atm.ATM;
import ru.otus.borisov.atm.AtmDepartment;
import ru.otus.borisov.atm.RubleBankNotesATM;
import ru.otus.borisov.atm.money.notes.BankNotesAcceptor;
import ru.otus.borisov.atm.money.ruble.RubleBankNote;
import ru.otus.borisov.atm.money.ruble.RubleBankNoteType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class AtmTest {

    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    public void getBalanceTest() {
        ATM<RubleBankNote> rubleAtm = new RubleBankNotesATM<>();
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        assertEquals(25000, rubleAtm.getBalance());
    }

    @Test
    public void getMoneyTest() {
        ATM<RubleBankNote> rubleAtm = new RubleBankNotesATM<>();
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtm.getMoney(13500);
        assertEquals(25000 - 13500, rubleAtm.getBalance());
    }

    @Test
    public void putMoneyTest() {
        ATM<RubleBankNote> rubleAtm = new RubleBankNotesATM<>();
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        assertEquals(50000, rubleAtm.getBalance());
    }

    @Test
    public void notEnoughMoneyTest() {
        ATM<RubleBankNote> rubleAtm = new RubleBankNotesATM<>();
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtm.getMoney(27000);
        assertTrue(errContent.toString().contains(BankNotesAcceptor.ERROR_CANT_GET_SUM));
    }

    @Test
    public void returnToDefaultTest() {
        RubleBankNotesATM<RubleBankNote> rubleAtm = new RubleBankNotesATM<>();
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtm.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtm.saveDefaultState();

        rubleAtm.getMoney(13500);
        assertEquals(50000 - 13500, rubleAtm.getBalance());

        rubleAtm.returnToDefaultState();
        assertEquals(50000, rubleAtm.getBalance());
    }

    @Test
    public void departmentTest() {
        AtmDepartment department = new AtmDepartment();

        String rubleAtmOnSulimovaAddress = "Ekaterinburg, Sulimova 50";
        RubleBankNotesATM<RubleBankNote> rubleAtmOnSulimova = new RubleBankNotesATM<>();
        rubleAtmOnSulimova.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtmOnSulimova.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtmOnSulimova.saveDefaultState();
        department.addAtm(rubleAtmOnSulimovaAddress, rubleAtmOnSulimova);

        String rubleAtmOnLeninaAddress = "Ekaterinburg, Lenina 49";
        RubleBankNotesATM<RubleBankNote> rubleAtmOnLenina = new RubleBankNotesATM<>();
        rubleAtmOnLenina.putMoney(getTwentyFiveThousandBankNotesArray());
        rubleAtmOnLenina.saveDefaultState();
        department.addAtm(rubleAtmOnLeninaAddress, rubleAtmOnLenina);

        rubleAtmOnSulimova.getMoney(15000);
        rubleAtmOnLenina.getMoney(10000);

        assertEquals(75000 - 15000 - 10000, department.getAmount());

        department.returnAtmDepartmentToDefault();

        assertEquals(75000, department.getAmount());
    }

    private static Collection<RubleBankNote> getTwentyFiveThousandBankNotesArray() {
        List<RubleBankNote> notes = new ArrayList<>();
        // add 3000 rubles by 100 ruble banknotes
        for (int i = 0; i < 30; i++) {
            notes.add(new RubleBankNote(RubleBankNoteType.ONE_HUNDRED));
        }

        // add 10000 rubles by 500 ruble banknotes
        for (int i = 0; i < 20; i++) {
            notes.add(new RubleBankNote(RubleBankNoteType.FIVE_HUNDRED));
        }

        // add 7000 rubles by 1000 ruble banknotes
        for (int i = 0; i < 7; i++) {
            notes.add(new RubleBankNote(RubleBankNoteType.ONE_THOUSAND));
        }

        // add 5000 rubles by 5000 ruble banknotes
        notes.add(new RubleBankNote(RubleBankNoteType.FIVE_THOUSAND));

        return notes;
    }
}
