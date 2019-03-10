package ru.otus.borisov.atm.money;

import java.util.Collection;

public interface MoneyProcessor {

    public int countBalance();
    public Collection<Money> countMoney(int sum);
}
