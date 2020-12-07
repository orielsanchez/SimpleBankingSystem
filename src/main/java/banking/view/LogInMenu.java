package banking.view;

import banking.Main;
import banking.model.Account;

public class LogInMenu {
    private Account logInAccount = null;

    public LogInMenu(Account account) {
        logInAccount = account;
    }

    public void show() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
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