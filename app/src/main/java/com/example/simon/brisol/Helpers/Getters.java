package com.example.simon.brisol.Helpers;

/**
 * Created by SIMON on 8/6/2017.
 */

public class Getters {

    private String _notName;
    private String _notType;
    private String _date;
    private String _transactions;
    private String _debit;
    private String _credit;
    private String _balance;


    public Getters(String notName, String notType) {
        this._notName = notName;
        this._notType = notType;
    }

    public Getters() {

    }


    public void set_notName(String _notName) {
        this._notName = _notName;
    }

    public void set_notType(String _notType) {
        this._notType = _notType;
    }

    public void set_balance(String _balance) {
        this._balance = _balance;
    }

    public void set_credit(String _credit) {
        this._credit = _credit;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_debit(String _debit) {
        this._debit = _debit;
    }

    public void set_transactions(String _transactions) {
        this._transactions = _transactions;
    }


    public String get_notName() {
        return _notName;
    }

    public String get_notType() {
        return _notType;
    }

    public String get_balance() {
        return _balance;
    }

    public String get_credit() {
        return _credit;
    }

    public String get_date() {
        return _date;
    }

    public String get_debit() {
        return _debit;
    }

    public String get_transactions() {
        return _transactions;
    }
}
