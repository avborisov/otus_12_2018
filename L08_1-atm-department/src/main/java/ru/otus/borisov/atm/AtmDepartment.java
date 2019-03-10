package ru.otus.borisov.atm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AtmDepartment {

    // only one ATM for unique address
    private Map<String, ATM> atmMap;

    public AtmDepartment() {
        atmMap = new HashMap<>();
    }

    public void addAtm(String address, ATM atm) {
        atmMap.put(address, atm);
    }

    public void removeAtm(String address) {
        atmMap.remove(address);
    }

    public int getAmount() {
        int amount = 0;
        for (String address : atmMap.keySet()) {
            ATM atm = atmMap.get(address);
            amount += atm.getBalance();
        }
        return amount;
    }

    public void returnAtmDepartmentToDefault() {
        for (String address : atmMap.keySet()) {
            ATM atm = atmMap.get(address);
            if (!atm.returnToDefaultState()) {
                System.out.println("Can't return ATM to default state for address: " + address);
            }
        }
    }

}
