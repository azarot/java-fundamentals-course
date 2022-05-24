package com.bobocode.homework;

import com.bobocode.data.Accounts;
import com.bobocode.model.Account;

import java.util.Comparator;

public class ComparatorDemoApp {
    public static void main(String[] args) {
        Comparator<Account> accountComparator = new RandomFieldComparator<>(Account.class, false);
        System.out.println(accountComparator);
        Accounts.generateAccountList(10)
                .stream()
                .sorted(accountComparator)
                .forEach(System.out::println);
    }
}
