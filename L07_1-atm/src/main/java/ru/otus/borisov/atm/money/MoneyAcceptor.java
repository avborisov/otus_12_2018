package ru.otus.borisov.atm.money;

import java.util.Collection;

public interface MoneyAcceptor<T extends Money> {

    public int getBalance();
    public boolean putMoney(Collection<T> money);
    public Collection<T> pullMoney(int sum);
    public boolean isWrongTypeOfMoney(T money);

}
