package ru.otus.borisov.atm.money;

public interface MoneyHolder {

    public int getMoneyCount();
    public int getMaxCount();
    public boolean put();
    public boolean pull();

}
