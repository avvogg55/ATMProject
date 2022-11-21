package com.company;

public class Account {
    private String cardNumber;
    private String password;
    private int balance;

    @Override
    public String toString() {
        return "Ваш номер карты ='" + cardNumber + '\'' +
                ", Ваш баланс = " + balance ;
    }

    public Account(){

    }

    public Account(String cardNumber, String password, int balance) {
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
