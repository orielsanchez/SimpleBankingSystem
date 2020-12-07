package banking.controller;


import banking.Main;
import banking.model.Account;

public class CreateAccount {
    public CreateAccount() {
        //Create the account
        Account account = Account.createAccount();

        //Add the account to the bank database
//        Main.database.add(account);
        Main.database.insert(String.valueOf(account.getID()), account.getPIN(), account.getBalance());

        //Print account information
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getID());
        System.out.println("Your card PIN:");
        System.out.println(account.getPIN());

        System.out.println();
        account = null;
        Main.mainMenu.show();
    }
}