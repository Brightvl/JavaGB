package ru.gb.core.Lesson4.hw4.custom_exeptions;

public class InsufficientFundsException extends Exception {

    private final double accountBalance;
    private final double withdrawalAmount;

    public InsufficientFundsException(String message, double accountBalance, double withdrawalAmount) {
        super(message);
        this.accountBalance = accountBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public double showLack() {
        return withdrawalAmount - accountBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }
}
