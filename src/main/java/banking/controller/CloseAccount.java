package banking.controller;

import banking.Main;
import banking.model.Account;

public class CloseAccount {

    // Updates funds on account
    public static void closeAccount(Account account) {
        Main.database.delete(String.valueOf(account.getID()));
    }
}

