package banking.view;

import banking.Main;
import banking.controller.UpdateAccount;
import banking.model.Account;

public class LogInMenu {
    private Account logInAccount = null;

    public LogInMenu(Account account) {
        logInAccount = account;
    }

    public void show() {
        System.out.println("1. Balance");
        System.out.println("2. Add Funds");
        System.out.println("3. Log out");
        System.out.println("4. Delete Account");
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
                System.out.println("You're balance is now " + logInAccount.getBalance() + "\n");
                show();
                break;
            case 3:
                logInAccount = null;
                System.out.println("You have successfully logged out!");
                break;
            case 4:
                System.out.println("Are you sure you want to delete your account?");
                //DeleteAccount();
            case 0:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                break;
        }
    }
}