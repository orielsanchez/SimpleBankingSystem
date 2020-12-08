package banking.controller;

import banking.Main;
import banking.model.Account;

import java.sql.SQLException;

public class TransferFunds {

    public static boolean transferFunds(Account account) {
        boolean keepGoing = true;
        String amt;
        String recipientCardNumber = null;
        int amount = 0;
        while (keepGoing) {

            System.out.println("Enter recipient's card number: ");
            recipientCardNumber = Main.in.next();

            boolean isRecipientCardNumberValid = false;
            while (!isRecipientCardNumberValid) {
                while (recipientCardNumber.length() != 16) {
                    System.out.println("Probably you made a mistake in the card number. Please try again!");
                    System.out.println("Enter recipient's card number: ");
                    recipientCardNumber = Main.in.next();
                }
                String numForCheck = recipientCardNumber.substring(0, 15);
                String checkNum = numForCheck + Account.addLuhnNum(numForCheck);
                if (recipientCardNumber.equals(String.valueOf(account.getID()))) {
                    System.out.println("You can't transfer money to the same account!");
                    System.out.println("Enter recipient's card number: ");
                    recipientCardNumber = Main.in.next();
                } else if (!recipientCardNumber.equals(checkNum)) {
                    System.out.println("Probably you made a mistake in the card number. Please try again!");
                    System.out.println("Enter recipient's card number: ");
                    recipientCardNumber = Main.in.next();
                } else {
                    isRecipientCardNumberValid = true;
                }
            }

            Account recipientAccount = Main.database.getAccount(Long.parseLong(recipientCardNumber));
            if (recipientAccount == null) {
                System.out.println("Such a card does not exist.");
            } else {
                System.out.println("Enter amount to transfer from your account: ");

                    try {
                        amt = Main.in.next();
                        if (Integer.parseInt(amt) > account.getBalance()) {
                            System.out.println("Not enough money!");
                            return false;
                        }

                        amount = Integer.parseInt(amt);
                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }


                keepGoing = false;
            }
        }

        try {
            Main.database.transfer(String.valueOf(account.getID()), recipientCardNumber, amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
