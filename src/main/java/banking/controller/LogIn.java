package banking.controller;

import banking.Main;
import banking.model.Account;
import banking.view.LogInMenu;

public class LogIn {
    public LogIn() {
        //Type in ID and PIN
        System.out.println("Enter your card number:");
        long id = Main.in.nextLong();
        System.out.println("Enter your PIN:");
        String PIN = Main.in.next();
        System.out.println();
        //Judge ID and PIN
        Account account;
        String numForCheck = String.valueOf(id).substring(0, 15);
        String checkNum = numForCheck + Account.addLuhnNum(numForCheck);
        if(!checkNum.equals(String.valueOf(id))) {
            account = null;
        }
        else {
            account = Main.database.getAccount(id);
        }
        if( account == null || !PIN.equals(account.getPIN())) {
            System.out.println("Wrong card number or PIN!\n");
        } else {
            System.out.println("You have successfully logged in!");
            System.out.println();
            LogInMenu logInMenu = new LogInMenu(account);
            logInMenu.show();
        }
        Main.mainMenu.show();
    }
}