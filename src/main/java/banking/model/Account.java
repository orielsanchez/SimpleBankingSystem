package banking.model;

import java.util.Random;

/**
 * This class represent an bank account
 */
public class Account {
    private final long ID;
    private final String PIN;
    private int balance;    //cent

    private static String generatePIN() {
        Random random = new Random();
        //Generate random pin
        int pin = random.nextInt(10000);
        String temp = Integer.toString(pin);
        //If 4-bits pin
        if (temp.length() == 4)
            return temp;
        //Full MSB with zero when not 4-bits
        return "0".repeat(Math.max(0, 4 - temp.length())) +
                temp;
    }

    public static Account createAccount() {
        long ID = Long.parseLong(generateNumber());
        String PIN = generatePIN();
        return new Account(ID, PIN, 0);
    }

    private static String generateNumber() {
        Random random = new Random();
        String num = "400000" + (random.nextInt(899999999) + 100000000);
        return num + addLuhnNum(num);
    }

    public static String addLuhnNum(String numForCheck) {
        numForCheck += "0";
        int oddSum = 0;
        int evenSum = 0;
        for (int x = numForCheck.length() - 1; x >= 0; x--) {
            int currentNum = Character.getNumericValue(numForCheck.charAt(x));
            if (x % 2 != 0) {
                oddSum += currentNum;
            } else {
                if (currentNum * 2 > 9) {
                    evenSum += currentNum * 2 - 9;
                } else {
                    evenSum += currentNum * 2;
                }
            }
        }
        int controlSum = oddSum + evenSum;
        return String.valueOf(controlSum % 10 == 0 ? 0 : 10 - controlSum % 10);
    }

    /**
     * Create an account object by three arguments
     *
     * @param ID      card id
     * @param PIN     account PIN
     * @param balance account balance
     */
    public Account(long ID, String PIN, int balance) {
        this.ID = ID;
        this.PIN = PIN;
        this.balance = balance;
    }

    public String getPIN() {
        return PIN;
    }

    public long getID() {
        return ID;
    }

    public int getBalance() {
        return balance;
    }

    public void addFunds(int funds) {
        this.balance += funds;
    }
}