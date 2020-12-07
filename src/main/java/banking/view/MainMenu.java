package banking.view;


import banking.Main;
import banking.controller.CreateAccount;
import banking.controller.LogIn;

public class MainMenu {
    public void show() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        System.out.println();
        choose(Main.in.nextInt());
    }

    public void choose(int command) {
        System.out.println();
        switch (command) {
            case 1:
                new CreateAccount();
                break;
            case 2:
                new LogIn();
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