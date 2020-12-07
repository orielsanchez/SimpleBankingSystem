package banking.controller;

import banking.Main;
import banking.model.Account;

public class UpdateAccount {

    // Updates funds on account
    public static void updateAccount(Account account) {
        Main.database.update(String.valueOf(account.getID()), account.getBalance());
    }
}

