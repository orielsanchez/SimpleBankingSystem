package banking.view;

import banking.Main;
import banking.controller.CloseAccount;
import banking.controller.TransferFunds;
import banking.controller.UpdateAccount;
import banking.model.Account;

public class LogInMenu {
    private Account logInAccount = null;

    public LogInMenu(Account account) {
        logInAccount = account;
    }

    public void show() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close Account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        System.out.println();
        choose(Main.in.nextInt());
    }

    public void choose(int command) {
        System.out.println();
        switch (command) {
            case 1:
                System.out.println("Balance: " + logInAccount.getBalance());
                System.out.println();
                show();
                break;
            case 2:
                System.out.println("How much would you like to add?");
                int funds = -1;
                while (funds < 0) {
                    funds = Main.in.nextInt();
                }
                logInAccount.addFunds(funds);
                UpdateAccount.updateAccount(logInAccount);
                System.out.println("\nFunds successfully added!");
                System.out.println("Your balance is now " + logInAccount.getBalance() + "\n");
                show();
                break;
            case 3:
                boolean fundsTransferred = TransferFunds.transferFunds(logInAccount);
                if (fundsTransferred) {
                    logInAccount = Main.database.getAccount(logInAccount.getID());
                    System.out.println("Funds have been transferred!");
                }
                show();
                break;

            case 4:
                long accountNumber = logInAccount.getID();
                CloseAccount.closeAccount(logInAccount);
                logInAccount = null;
                System.out.println("Your account #" + accountNumber + " has been deleted.\n");
                break;
            case 5:
                logInAccount = null;
                System.out.println("You have successfully logged out!");
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                break;
        }
    }
}