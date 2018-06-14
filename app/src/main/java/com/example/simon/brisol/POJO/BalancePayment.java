package com.example.simon.brisol.POJO;

/**
 * Created by dennism501 on 8/14/17.
 */

public class BalancePayment {

    private String fname;
    private String lname;
    private int balanceAmount;

    public int getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(int balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    private String amountOwning;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAmountOwning() {
        return amountOwning;
    }

    public void setAmountOwning(String amountOwning) {
        this.amountOwning = amountOwning;
    }
}
