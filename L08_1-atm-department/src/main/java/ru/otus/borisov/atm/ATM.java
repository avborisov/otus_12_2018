package ru.otus.borisov.atm;

import ru.otus.borisov.atm.money.Money;

import java.util.Collection;

public interface ATM<T extends  Money> {

    public int getBalance();
    public Collection<T> getMoney(int sum);
    public void putMoney(Collection<T> note);
    public void saveDefaultState();
    public boolean returnToDefaultState();

}
